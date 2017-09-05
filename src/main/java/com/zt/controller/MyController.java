package com.zt.controller;

import com.zt.mongo.entity.Article;
import com.zt.mongo.service.ArticleService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/myController")
public class MyController {

    @Autowired
    private Client client;

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        SearchResponse sr = client.prepareSearch("song001")
                .execute().actionGet();
        //输出结果
        SearchHits hits = sr.getHits();
        System.out.println(hits.getAt(0).getSource().get("singer"));
        List<Article> list = articleService.findList();
        return "111";
    }


}
