package com.searchengine.indexservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * created by nikunjagarwal on 22-09-2022
 */
@Entity
@Table(name="url_metadata")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="url_id")
    private Long urlId;
    @Column(name="url", unique = true)
    private String url;
    @Column(name="redirected_url")
    private String redirectedUrl;
    @Column(name="s3_file_path")
    private String s3FilePath;
    @Column(name="title", columnDefinition="LONGTEXT")
    private String title;
    @Enumerated(EnumType.STRING)
    @Column(name="crawl_status")
    private CrawlStatus crawlStatus;
    @Column(name="http_status_code")
    private int httpStatusCode;
    @Column(name="error_message")
    private String errorMessage;
    @Column(name="retry_count")
    private int retryCount;
    @Column(name="page_rank")
    private int pageRank;
    @Column(name="last_crawled")
    private Date lastCrawled;
}
