package com.searchmetrics.n3jobservice;

import com.datastax.driver.core.LocalDate;
import org.apache.cassandra.io.sstable.CQLSSTableWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arobinson on 3/25/17.
 */
public class CSVImporter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CSVImporter.class);
    private static final String KEYSPACE = "n3jobservice";
    private static final String TABLE = "jobs";

    private static final String INSERT = MessageFormat.format(
            "INSERT INTO {0}.{1} (" +
                    "id, crawltype, url, priority, maxpages, realpages, " +
                    "keywordjson, ssojson, errorcount, crawlstatus, retries, " +
                    "crawlernode, crawlerpid, createdate, lastcrawl, " +
                    "jobsentstop, jobdone, callback, usesmurlid, datecreated, " +
                    "datedone" +
                    ") VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",
            KEYSPACE,
            TABLE
    );

    private static final String SCHEMA = MessageFormat.format(
            "CREATE TABLE {0}.{1} (" +
                    "id bigint," +
                    "crawltype text," +
                    "url text," +
                    "priority int," +
                    "maxpages int," +
                    "realpages int," +
                    "keywordjson text," +
                    "ssojson text," +
                    "errorcount int," +
                    "crawlstatus text," +
                    "retries int," +
                    "crawlernode int," +
                    "crawlerpid int," +
                    "createdate timestamp," +
                    "lastcrawl timestamp," +
                    "jobsentstop timestamp," +
                    "jobdone timestamp," +
                    "callback text," +
                    "usesmurlid boolean," +
                    "datecreated date," +
                    "datedone date," +
                    "PRIMARY KEY (id)" +
                    ")",
            KEYSPACE,
            TABLE
    );

    public static final CSVFormat INPUT_FORMAT = CSVFormat.MYSQL
            .withDelimiter(',')
            .withEscape('\\')
            .withHeader(CSV_HEADERS.class)
            .withNullString("\\N")
            .withQuote('"')
            .withSkipHeaderRecord(false);

    public static void main(String...args) throws IOException {
        CQLSSTableWriter writer = CQLSSTableWriter.builder()
                .inDirectory("/Users/arobinson/jobs_cqlsstable")
                .forTable(SCHEMA)
                .using(INSERT).build();

        INPUT_FORMAT.parse(new FileReader("/Users/arobinson/jobs.txt"))
                .forEach(
                        l -> {
                            processRow(l, writer);
                        }
                );
        writer.close();
        System.exit(0);
    }

    private static int i = 0;
    private static void processRow(CSVRecord csvRecord, CQLSSTableWriter cqlssTableWriter) {
        final List<Object> rowData = mapToCorrectTypes(csvRecord);
        try {
            cqlssTableWriter.addRow(rowData);
            if (++i % 10000 == 0)
                LOGGER.info("Processed record: {}", i);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Invalid input:\n\t{}", csvRecord);
        }
    }
    private static List<Object> mapToCorrectTypes(CSVRecord values) {
        List<Object> results = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            Object value = null;
            switch (i) {
                case 0:
                    value = Long.valueOf(values.get(i));
                    break;
                case 3:
                case 4:
                case 5:
                case 8:
                case 10:
                case 11:
                case 12:
                    value = null == values.get(i) ? null : Integer.valueOf(values.get(i));
                    break;
                case 13:
                case 14:
                case 15:
                case 16:
                    value = null == values.get(i) ? null : DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(values.get(i)).toDate();
                    break;
                case 18:
                    value = Boolean.valueOf(Integer.valueOf(values.get(i)) == 1 ? "true" : "false");
                    break;
                default:
                    value = values.get(i);
            }
            results.add(value);
        }

        // add date from createdate (13)
        String createDate = values.get(13);
        String jobDone = values.get(16);
        try {
            if (null != createDate) {
//                LOGGER.info("datecreated: {}", createDate.substring(0,10));
                results.add(converToLocalDate(createDate));
            }
            else
                results.add(null);

            if (null != jobDone)
                results.add(converToLocalDate(jobDone));
            else
                results.add(null);
        }
        catch (Exception e) {
            LOGGER.error("Invalid input for datecreated or datedone: {}", values.toString());
            e.printStackTrace();
            System.exit(1);
        }

        return results;
    }

    private static LocalDate converToLocalDate(final String dateTime) {
        final Integer years = Integer.valueOf(dateTime.substring(0,4));
        final Integer months = Integer.valueOf(dateTime.substring(5,7));
        final Integer days = Integer.valueOf(dateTime.substring(8,10));
        return LocalDate.fromYearMonthDay(years, months, days);
    }

    enum CSV_HEADERS {
        ID, CRAWL_TYPE, URL, PRIORITY, MAX_PAGES, REAL_PAGES, KEYWORD_JSON,
        SSO_JSON, ERROR_COUNT, CRAWL_STATUS, RETRIES, CRAWLER_NODE,
        CRAWLER_PID, CREATE_DATE, LAST_CRAWL, JOB_SENT_STOP, JOB_DONE,
        CALLBACK, USE_SM_URL_ID;
    }
}
