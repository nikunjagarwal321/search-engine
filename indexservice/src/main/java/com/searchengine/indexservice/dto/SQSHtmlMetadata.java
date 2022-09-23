package com.searchengine.indexservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * created by nikunjagarwal on 01-09-2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SQSHtmlMetadata {
    private Long urlId;
    private String url;
    private String redirectedUrl;
    private HttpStatus status;
    private String errorMessage;
    private String lastCrawledTime;
    private String s3Path;
}
