package com.searchengine.indexservice.repository;

import com.searchengine.indexservice.dto.CrawlStatus;
import com.searchengine.indexservice.dto.UrlMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

/**
 * created by nikunjagarwal on 22-09-2022
 */
@Repository
public interface UrlMetadataRepository extends JpaRepository<UrlMetadata, String> {

    @Transactional
    @Modifying
    @Query(value = "update url_metadata u set u.redirected_url = :redirectedUrl, " +
            "u.s3_file_path=:s3FilePath, u.title=:title, " +
            "u.crawl_status=:#{#crawlStatus.name()}, u.http_status_code=:httpStatusCode " +
//            "u.last_crawled=:lastCrawled " +
            "where u.url_id=:urlId",
            nativeQuery = true)
    int updateExistingUrl(@Param("redirectedUrl")String redirectedUrl,
                          @Param("s3FilePath")String s3FilePath,
                          @Param("title")String title,
                          @Param("crawlStatus")CrawlStatus crawlStatus,
                          @Param("httpStatusCode")int httpStatusCode,
//                          @Param("lastCrawled")Date lastCrawled,
                          @Param("urlId")Long urlId);

    @Query("select u from UrlMetadata u where url=:url")
    ArrayList<UrlMetadata> findByUrl(@Param("url")String url);

    @Transactional
    @Modifying
    @Query(value = "update url_metadata u set u.page_rank = :pageRank where u.url=:url", nativeQuery = true)
    int updatePagerank(@Param("pageRank")int pageRank, @Param("url")String url);

    @Transactional
    @Modifying
    @Query(value = "update url_metadata u set u.crawl_status = :crawlStatus, " +
            "u.http_status_code = :httpStatusCode, u.error_message = :errorMessage" +
            " where u.url=:urlId", nativeQuery = true)
    int updateFailedUrl(@Param("crawlStatus")CrawlStatus crawlStatus, @Param("httpStatusCode")int httpStatusCode,
                        @Param("errorMessage")String errorMessage, @Param("urlId")Long urlId);

    @Transactional
    @Modifying
    @Query(value = "update url_metadata u set u.retry_count = :retryCount where u.url=:urlId", nativeQuery = true)
    int updateRetryCount(@Param("retryCount")int retryCount,@Param("urlId")Long urlId);

}
