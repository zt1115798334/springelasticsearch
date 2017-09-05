package com.zt.es.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;

public interface EsService {

    void test();

    boolean save(String index, String type, String id, JSONObject jsonObject);

    boolean batchSave(String index, String type, String id, JSONObject jsonObject);

    JSONArray find(String index, String type);

    JSONArray find(String index, String type, TermQueryBuilder termQuery, RangeQueryBuilder rangeQuery);

    boolean delete(String index, String type,String id);

    long delete(String index, String type,String id,MatchQueryBuilder matchQuery);

    /**
     * https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-docs-update.html
     * @param index
     * @param type
     * @param id
     * @return
     */
    boolean update(String index, String type,String id);
}
