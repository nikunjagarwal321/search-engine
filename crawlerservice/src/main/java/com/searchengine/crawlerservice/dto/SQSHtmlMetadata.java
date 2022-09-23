package com.searchengine.crawlerservice.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * created by nikunjagarwal on 23-09-2022
 */
@Data
@Builder
public class SQSHtmlMetadata {
    private Long urlId;
    private String url;
    private String redirectedUrl;
    private HttpStatus status;
    private String errorMessage;
    private String lastCrawledTime;
    private String s3Path;
}
