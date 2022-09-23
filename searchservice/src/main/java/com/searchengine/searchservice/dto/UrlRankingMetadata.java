package com.searchengine.searchservice.dto;

import lombok.Builder;
import lombok.Data;

/**
 * created by nikunjagarwal on 22-09-2022
 */
@Data
@Builder
public class UrlRankingMetadata {
    private Long urlId;
    private Long wordCount;
    private Long titlePresenceCount;
    private Long pageRankCount;
    private Long wordCountRank;
    private Long titlePresenceCountRank;
    private Long pageRankCountRank;
    private Double weightedRank;
}
