package com.zt.es.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zt.mongo.entity.Article;
import org.elasticsearch.index.query.*;

import java.util.List;

public interface EsService {

    void test();

    boolean save(String index, String type, String id, JSONObject jsonObject);

    boolean save(String index, String type, JSONObject jsonObject);

    boolean batchSave(String index, String type, List<Article> articles);

    JSONArray find(String index, String type);

    JSONArray findTokenizer(String index, String content);

    JSONArray find(String index, String type, QueryBuilder query);

    JSONArray find(String index, String type, TermQueryBuilder termQuery, RangeQueryBuilder rangeQuery);

    JSONArray findByPage(String index, String type, Integer pageSize, Integer pageNo);

    boolean delete(String index, String type, String id);

    long delete(String index, String type, String id, MatchQueryBuilder matchQuery);

    /**
     * https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-docs-update.html
     */
    boolean update(String index, String type, String id);

    /**
     * 创建索引
     *
     * @param index 索引名
     */
    void createCluterName(String index);

    /**
     * 创建mapping(feid("indexAnalyzer","ik")该字段分词IK索引 ；feid("searchAnalyzer","ik")该字段分词ik查询；具体分词插件请看IK分词插件说明)
     *
     * @param index 索引名称；
     * @param type  索引类型
     */
    void createMapping(String index, String type);

    boolean isExistsIndex(String index);

}
