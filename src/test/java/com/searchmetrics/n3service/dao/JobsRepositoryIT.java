package com.searchmetrics.n3service.dao;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.searchmetrics.n3jobservice.dao.CassandraConf;
import com.searchmetrics.n3jobservice.dao.JobsRepository;
import com.searchmetrics.n3jobservice.dto.Job;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.cql.CqlIdentifier;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Created by arobinson on 3/25/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CassandraConf.class)
public class JobsRepositoryIT {
    public static final Logger LOGGER = LoggerFactory.getLogger(JobsRepositoryIT.class);

    // Added additional time to bring up the server, was failing on work machine...default is 10000 ms
    public static final Long EMBEDDED_SERVER_STARTUP_TIMEOUT = 30000L;

    public static final String KEYSPACE_CREATION_QUERY =
            "CREATE KEYSPACE IF NOT EXISTS n3jobservice WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '1' };";

    public static final String KEYSPACE_ACTIVATE_QUERY = "USE n3jobservice;";

    public static final String DATA_TABLE_NAME = "jobs";

    @Autowired
    private JobsRepository jobsRepository;

    @Autowired
    private CassandraAdminOperations adminTemplate;

    @BeforeClass
    public static void startCassandraEmbedded() throws InterruptedException, TTransportException, ConfigurationException, IOException {
        EmbeddedCassandraServerHelper.startEmbeddedCassandra(EMBEDDED_SERVER_STARTUP_TIMEOUT);
        final Cluster cluster = Cluster.builder().addContactPoints("127.0.0.1").withPort(9142).build();
        LOGGER.info("Server Started at 127.0.0.1:9142... ");
        final Session session = cluster.connect();
        session.execute(KEYSPACE_CREATION_QUERY);
        session.execute(KEYSPACE_ACTIVATE_QUERY);
        LOGGER.info("KeySpace created and activated.");
        Thread.sleep(10000);
    }

    @Before
    public void createTable() throws InterruptedException, TTransportException, ConfigurationException, IOException {
        adminTemplate.createTable(true, CqlIdentifier.cqlId(DATA_TABLE_NAME), Job.class, new HashMap<String, Object>());
    }

    @Test
    public void test_newJob() {
        Job newJob = new Job(1234567L, "prod.wago.com", 10000, "sso", null,
                null, LocalDateTime.now(), "someURL", true);
        jobsRepository.save(newJob);
        Assert.assertTrue("", jobsRepository.findById(1234567L).isPresent());
    }
    @After
    public void dropTable() {
        adminTemplate.dropTable(CqlIdentifier.cqlId(DATA_TABLE_NAME));
    }

    @AfterClass
    public static void stopCassandraEmbedded() {
        EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
    }
}
