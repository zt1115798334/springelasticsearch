package com.zt.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zt.es.service.EsService;
import com.zt.mongo.entity.Article;
import com.zt.mongo.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/myController")
public class MyController {

    private static final String index = "jdjr";
    private static final String type = "article";


    @Autowired
    private ArticleService articleService;

    @Autowired
    private EsService esService;

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        esService.test();
        return "zt";
    }

    @RequestMapping("/add")
    @ResponseBody
    public String add(String id) {
        Article article = articleService.findById(id);
        esService.save(index, type, article.getId(), JSON.parseObject(JSON.toJSONString(article)));
        return "zt";
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public String findAll() {
        JSONArray jsonArray = esService.find(index, type);
        List<Article> articles = JSONArray.parseArray(jsonArray.toJSONString(), Article.class);
        System.out.println("articles.size = " + articles.size());
        return "zt";
    }

    @RequestMapping("/findQuery")
    @ResponseBody
    public String findQuery() {
        JSONArray jsonArray = esService.find(index, type);
        List<Article> articles = JSONArray.parseArray(jsonArray.toJSONString(), Article.class);
        System.out.println("articles.size = " + articles.size());
        return "zt";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(String id) {
        esService.delete(index, type, id);
        return "zt";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String update(String id) {
        esService.update(index, type, id);
        return "zt";
    }

}
