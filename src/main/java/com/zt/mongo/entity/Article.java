package com.zt.mongo.entity;


import com.zt.base.entity.BasePageRequest;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "article")
public class Article extends BasePageRequest {
    /**
     *
     */
    private static final long serialVersionUID = 9201034849892179274L;
    @Id
    @Indexed(unique = true)
    private String id;

    @Field("title")
    private String title;// 标题

    @Field("media")
    private String media;// 媒体名称

    @Field("media_type")
    private String mediaType;    //媒体类型

    @Field("content")
    private String content;// 内容

    @Field("publish_datetime")
    private Date publishDateTime;// 发布日期时间

    @Field("publish_date")
    private String publishDate;// 发布日期

    @Field("author")
    private String author;// 作者

    @Field("keywords")
    private String keywords;// 关键词

    @Field("url")
    private String url;// 原文链接

    @Field("url_md5")
    private String urlMD5;// url的MD5值

    @Field("crawler_date")
    private String crawlerDate;// 采集时间，格式：yyyy-MM-dd

    @Field("crawler_datetime")
    private Date crawlerDateTime;// 采集时间

    @Field("article_fingerprint")
    private String articleFingerprint;//文章指纹

    @Field("hot")
    private Integer hot;//热度

    @Field("is_analyzed")
    private String isAnalyzed;//是否已经分析，已经分析：是；未分析：否

    @Field("task_id")
    private String taskId;//晴空系统的采集任务id，用于到晴空系统中反查数据

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishDateTime() {
        return publishDateTime;
    }

    public void setPublishDateTime(Date publishDateTime) {
        this.publishDateTime = publishDateTime;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlMD5() {
        return urlMD5;
    }

    public void setUrlMD5(String urlMD5) {
        this.urlMD5 = urlMD5;
    }

    public String getCrawlerDate() {
        return crawlerDate;
    }

    public void setCrawlerDate(String crawlerDate) {
        this.crawlerDate = crawlerDate;
    }

    public Date getCrawlerDateTime() {
        return crawlerDateTime;
    }

    public void setCrawlerDateTime(Date crawlerDateTime) {
        this.crawlerDateTime = crawlerDateTime;
    }

    public String getArticleFingerprint() {
        return articleFingerprint;
    }

    public void setArticleFingerprint(String articleFingerprint) {
        this.articleFingerprint = articleFingerprint;
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }

    public String getIsAnalyzed() {
        return isAnalyzed;
    }

    public void setIsAnalyzed(String isAnalyzed) {
        this.isAnalyzed = isAnalyzed;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
