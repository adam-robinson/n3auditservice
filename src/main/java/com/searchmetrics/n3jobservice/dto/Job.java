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
            @JsonProperty("sm_url_id") Boolean useSMUrlId) {
        this.id = checkNotNull(id);
        this.url = checkNotNull(url);
        this.maxPages = checkNotNull(maxPages);
        this.crawlType = checkNotNull(crawlType);
        this.keywordJSON = keywordJSON;
        this.ssoAuditJSON = ssoAuditJSON;
        this.createDate = checkNotNull(createDate);
        this.callback = checkNotNull(callback);
        this.useSMUrlId = checkNotNull(useSMUrlId);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Job job = (Job) o;

        if (!id.equals(job.id)) return false;
        if (!url.equals(job.url)) return false;
        if (!priority.equals(job.priority)) return false;
        if (!maxPages.equals(job.maxPages)) return false;
        if (!crawlType.equals(job.crawlType)) return false;
        if (realPages != null ? !realPages.equals(job.realPages) : job.realPages != null) return false;
        if (keywordJSON != null ? !keywordJSON.equals(job.keywordJSON) : job.keywordJSON != null) return false;
        if (ssoAuditJSON != null ? !ssoAuditJSON.equals(job.ssoAuditJSON) : job.ssoAuditJSON != null) return false;
        if (errorCount != null ? !errorCount.equals(job.errorCount) : job.errorCount != null) return false;
        if (!crawlStatus.equals(job.crawlStatus)) return false;
        if (retries != null ? !retries.equals(job.retries) : job.retries != null) return false;
        if (crawlerNode != null ? !crawlerNode.equals(job.crawlerNode) : job.crawlerNode != null) return false;
        if (crawlerPid != null ? !crawlerPid.equals(job.crawlerPid) : job.crawlerPid != null) return false;
        if (!createDate.equals(job.createDate)) return false;
        if (lastCrawl != null ? !lastCrawl.equals(job.lastCrawl) : job.lastCrawl != null) return false;
        if (jobStopSent != null ? !jobStopSent.equals(job.jobStopSent) : job.jobStopSent != null) return false;
        if (jobDone != null ? !jobDone.equals(job.jobDone) : job.jobDone != null) return false;
        if (!callback.equals(job.callback)) return false;
        return useSMUrlId.equals(job.useSMUrlId);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + priority.hashCode();
        result = 31 * result + maxPages.hashCode();
        result = 31 * result + crawlType.hashCode();
        result = 31 * result + (realPages != null ? realPages.hashCode() : 0);
        result = 31 * result + (keywordJSON != null ? keywordJSON.hashCode() : 0);
        result = 31 * result + (ssoAuditJSON != null ? ssoAuditJSON.hashCode() : 0);
        result = 31 * result + (errorCount != null ? errorCount.hashCode() : 0);
        result = 31 * result + crawlStatus.hashCode();
        result = 31 * result + (retries != null ? retries.hashCode() : 0);
        result = 31 * result + (crawlerNode != null ? crawlerNode.hashCode() : 0);
        result = 31 * result + (crawlerPid != null ? crawlerPid.hashCode() : 0);
        result = 31 * result + createDate.hashCode();
        result = 31 * result + (lastCrawl != null ? lastCrawl.hashCode() : 0);
        result = 31 * result + (jobStopSent != null ? jobStopSent.hashCode() : 0);
        result = 31 * result + (jobDone != null ? jobDone.hashCode() : 0);
        result = 31 * result + callback.hashCode();
        result = 31 * result + useSMUrlId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", priority=" + priority +
                ", maxPages=" + maxPages +
                ", crawlType='" + crawlType + '\'' +
                ", realPages=" + realPages +
                ", keywordJSON='" + keywordJSON + '\'' +
                ", ssoAuditJSON='" + ssoAuditJSON + '\'' +
                ", errorCount=" + errorCount +
                ", crawlStatus='" + crawlStatus + '\'' +
                ", retries=" + retries +
                ", crawlerNode=" + crawlerNode +
                ", crawlerPid=" + crawlerPid +
                ", createDate=" + createDate +
                ", lastCrawl=" + lastCrawl +
                ", jobStopSent=" + jobStopSent +
                ", jobDone=" + jobDone +
                ", callback='" + callback + '\'' +
                ", useSMUrlId=" + useSMUrlId +
                '}';
    }

    public static class AggResult {

    }
}
