package com.searchengine.indexservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

/**
 * created by nikunjagarwal on 01-09-2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlMapping {
    @Field(type= FieldType.Object)
    private List<UrlMetadata> urls;
}
