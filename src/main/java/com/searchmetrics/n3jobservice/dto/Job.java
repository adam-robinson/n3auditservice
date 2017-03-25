package com.searchmetrics.n3jobservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import java.time.LocalDateTime;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by arobinson on 3/25/17.
 */
@Table(value = "jobs")
public class Job {
    @PrimaryKeyColumn(
            name = "id",
            ordinal = 0,
            type = PrimaryKeyType.PARTITIONED)
    private final Long id;
    @Column
    private final String url;
    @Column
    private Integer priority;
    @Column
    private final Integer maxPages;
    @Column
    private final String crawlType;
    @Column
    private Integer realPages = 0;
    @Column
    private final String keywordJSON;
    @Column
    private final String ssoAuditJSON;
    @Column
    private Integer errorCount = 0;
    @Column
    private String crawlStatus;
    @Column
    private Integer retries = 0;
    @Column
    private Integer crawlerNode;
    @Column
    private Integer crawlerPid;
    @Column
    private final LocalDateTime createDate;
    @Column
    private LocalDateTime lastCrawl;
    @Column
    private LocalDateTime jobStopSent;
    @Column
    private LocalDateTime jobDone;
    @Column
    private final String callback;
    @Column
    private final Boolean useSMUrlId;

    @JsonCreator
    public Job(
            @JsonProperty final Long id,
            @JsonProperty final String url,
            @JsonProperty("max_pages") final Integer maxPages,
            @JsonProperty("type") final String crawlType,
            @JsonProperty("keywjson") final String keywordJSON,
            @JsonProperty("deepson") final String ssoAuditJSON,
            @JsonProperty final LocalDateTime createDate,
            @JsonProperty final String callback,
            @JsonProperty("sm_url_id") Integer useSMUrlId) {
        this.id = checkNotNull(id);
        this.url = checkNotNull(url);
        this.maxPages = checkNotNull(maxPages);
        this.crawlType = checkNotNull(crawlType);
        this.keywordJSON = checkNotNull(keywordJSON);
        this.ssoAuditJSON = checkNotNull(ssoAuditJSON);
        this.createDate = checkNotNull(createDate);
        this.callback = checkNotNull(callback);
        this.useSMUrlId = null != useSMUrlId && useSMUrlId > 0 ? true : false;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getMaxPages() {
        return maxPages;
    }

    public String getCrawlType() {
        return crawlType;
    }

    public Integer getRealPages() {
        return realPages;
    }

    public void setRealPages(Integer realPages) {
        this.realPages = realPages;
    }

    public String getKeywordJSON() {
        return keywordJSON;
    }

    public String getSsoAuditJSON() {
        return ssoAuditJSON;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    public String getCrawlStatus() {
        return crawlStatus;
    }

    public void setCrawlStatus(String crawlStatus) {
        this.crawlStatus = crawlStatus;
    }

    public Integer getRetries() {
        return retries;
    }

    public void setRetries(Integer retries) {
        this.retries = retries;
    }

    public Integer getCrawlerNode() {
        return crawlerNode;
    }

    public void setCrawlerNode(Integer crawlerNode) {
        this.crawlerNode = crawlerNode;
    }

    public Integer getCrawlerPid() {
        return crawlerPid;
    }

    public void setCrawlerPid(Integer crawlerPid) {
        this.crawlerPid = crawlerPid;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public LocalDateTime getLastCrawl() {
        return lastCrawl;
    }

    public void setLastCrawl(LocalDateTime lastCrawl) {
        this.lastCrawl = lastCrawl;
    }

    public LocalDateTime getJobStopSent() {
        return jobStopSent;
    }

    public void setJobStopSent(LocalDateTime jobStopSent) {
        this.jobStopSent = jobStopSent;
    }

    public LocalDateTime getJobDone() {
        return jobDone;
    }

    public void setJobDone(LocalDateTime jobDone) {
        this.jobDone = jobDone;
    }

    public String getCallback() {
        return callback;
    }

    public Boolean getUseSMUrlId() {
        return useSMUrlId;
    }
}
