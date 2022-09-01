package com.searchengine.indexservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by nikunjagarwal on 01-09-2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SQSHtmlMetadata {
    private Integer urlId;
    private String url;
    private String s3Path;
}
