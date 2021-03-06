package com.zt.es.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zt.es.service.EsService;
import com.zt.mongo.entity.Article;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
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
    public boolean save(String index, String type, JSONObject jsonObject) {
        IndexResponse response = client.prepareIndex(index, type)
                .setSource(jsonObject).execute().actionGet();
        return false;
    }

    @Override
    public boolean batchSave(String index, String type, List<Article> articles) {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        articles.stream().forEach(article -> {
            String id = article.getId();
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(article));
            bulkRequest.add(client.prepareIndex(index, type, id).setSource(jsonObject));
        });
        BulkResponse response = bulkRequest.execute().actionGet();
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
    public JSONArray findTokenizer(String index, String content) {
//        IndicesAdminClient indicesAdminClient = client.admin().indices();
//        AnalyzeRequestBuilder request = new AnalyzeRequestBuilder(indicesAdminClient, AnalyzeAction.INSTANCE, index, content);
//        request.setAnalyzer("ik");
//        request.setTokenizer("ik");
//        // Analyzer（分析器）、Tokenizer（分词器）
//        List<AnalyzeResponse.AnalyzeToken> ikTokenList = request.execute().actionGet().getTokens();
//        // listAnalysis中的结果就是分词的结果
//        JSONArray result = JSONArray.parseArray(JSON.toJSONString(ikTokenList));

        AnalyzeResponse response = client.admin().indices()
                .prepareAnalyze(content)//内
                .setAnalyzer("ik")//指定分词器
                .execute().actionGet();//执行
        List<AnalyzeResponse.AnalyzeToken> ikTokenList = response.getTokens();
        JSONArray result = JSONArray.parseArray(JSON.toJSONString(ikTokenList));
        return result;
    }

    @Override
    public JSONArray find(String index, String type, QueryBuilder query) {
        SearchResponse response = client.prepareSearch(index)
                .setTypes(type)
//                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(query)
//                .setExplain(true)
                .get();
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
    public JSONArray findByPage(String index, String type, Integer pageSize, Integer pageNo) {
        SearchResponse response = client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setFrom((pageNo - 1) * pageSize)
                .setSize(pageSize)
                .setExplain(true)
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

    @Override
    public void createCluterName(String index) {
        client.admin().indices().prepareCreate(index).execute().actionGet();
    }

    @Override
    public void createMapping(String index, String type) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .startObject("properties")
                    .startObject("title").field("type", "string").field("store", "yes")
                    .field("indexAnalyzer", "ik").field("searchAnalyzer", "ik").endObject()
                    .startObject("media").field("type", "string").field("store", "yes").endObject()
                    .startObject("mediaType").field("type", "string").field("store", "yes").endObject()
                    .startObject("content").field("type", "string").field("store", "yes").endObject()
                    .startObject("publishDateTime").field("type", "date").field("store", "yes").endObject()
                    .startObject("publishDate").field("type", "string").field("store", "yes").endObject()
                    .startObject("author").field("type", "string").field("store", "yes").endObject()
                    .startObject("keywords").field("type", "string").field("store", "yes").endObject()
                    .startObject("url").field("type", "string").field("store", "yes").endObject()
                    .startObject("urlMD5").field("type", "string").field("store", "yes").endObject()
                    .startObject("crawlerDate").field("type", "string").field("store", "yes").endObject()
                    .startObject("crawlerDateTime").field("type", "date").field("store", "yes").endObject()
                    .startObject("articleFingerprint").field("type", "string").field("store", "yes").endObject()
                    .startObject("hot").field("type", "long").field("store", "yes").endObject()
                    .startObject("isAnalyzed").field("type", "string").field("store", "yes").endObject()
                    .startObject("taskId").field("type", "string").field("store", "yes").endObject()
                    .endObject()
                    .endObject();
            PutMappingRequest mapping = Requests.putMappingRequest(index).type(type).source(builder);
            client.admin().indices().putMapping(mapping).actionGet();
        } catch(IOException e) {
            e.printStackTrace();
        }
        //field("store", "yes")并不是必需的，但是field("type", "string")是必需要存在的，不然会报错。
    }

    @Override
    public boolean isExistsIndex(String index) {
        IndicesExistsResponse response = client.admin().indices().exists(
                new IndicesExistsRequest().indices(new String[]{index})).actionGet();
        return response.isExists();
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
