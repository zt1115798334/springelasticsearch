package com.zt.es.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zt.es.service.EsService;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Service
public class EsServiceImpl implements EsService {
    @Autowired
    private Client client;

    @Override
    public void test() {
        SearchResponse sr = client.prepareSearch("song001")
                .execute().actionGet();
        SearchHits hits = sr.getHits();
        System.out.println(hits.getAt(0).getSource().get("singer"));
    }

    @Override
    public boolean save(String index, String type, String id, JSONObject jsonObject) {
        IndexResponse response = client.prepareIndex(index, type, id)
                .setSource(jsonObject).execute().actionGet();
        return true;
    }

    @Override
    public boolean batchSave(String index, String type, String id, JSONObject jsonObject) {

        return false;
    }

    @Override
    public JSONArray find(String index, String type) {
        SearchResponse response = client.prepareSearch(index)
                .setTypes(type).get();
        JSONArray result = readSearchResponse(response);
        return result;
    }

    @Override
    public JSONArray find(String index, String type, TermQueryBuilder termQuery, RangeQueryBuilder rangeQuery) {
        SearchResponse response = client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(termQuery)
                .setPostFilter(rangeQuery)
                .setFrom(0).setSize(60).setExplain(true)
                .get();
        JSONArray result = readSearchResponse(response);
        return result;
    }

    @Override
    public boolean delete(String index, String type, String id) {
        DeleteResponse response = client.prepareDelete(index, type, id).get();
        return false;
    }

    @Override
    public long delete(String index, String type, String id, MatchQueryBuilder matchQuery) {
        BulkByScrollResponse response =
                DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                        .filter(matchQuery)
                        .source("persons")
                        .get();
        long deleted = response.getDeleted();
        return deleted;
    }

    @Override
    public boolean update(String index, String type, String id) {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(index);
        updateRequest.type(type);
        updateRequest.id(id);
        try {
            updateRequest.doc(jsonBuilder()
                    .startObject()
                    .field("isAnalyzed", "否")
                    .endObject());
            client.update(updateRequest).get();
        } catch(IOException e) {
            e.printStackTrace();
        } catch(InterruptedException e) {
            e.printStackTrace();
        } catch(ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    private JSONArray readSearchResponse(SearchResponse response) {
        SearchHits hits = response.getHits();
        JSONArray result = new JSONArray();
        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = JSON.parseObject(json);
            } catch(JSONException e) {
                e.printStackTrace();
            }
            result.add(jsonObject);
        }
        return result;
    }
}
