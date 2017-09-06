package com.zt.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zt.es.service.EsService;
import com.zt.mongo.entity.Article;
import com.zt.mongo.service.ArticleService;
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

    private static final String indexTokenizer = "jdjrik";
    private static final String typeTokenizer = "articleik";


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

    @RequestMapping("/batchAdd")
    @ResponseBody
    public String batchAdd() {
        List<Article> articles = articleService.findList();
        articles.forEach(article -> {
            esService.save(index, type, article.getId(), JSON.parseObject(JSON.toJSONString(article)));
        });
        return "zt";
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public Object findAll() {
        JSONArray jsonArray = esService.find(index, type);
        List<Article> articles = JSONArray.parseArray(jsonArray.toJSONString(), Article.class);
        System.out.println("articles.size = " + articles.size());
        return jsonArray;
    }

    @RequestMapping("/findQuery")
    @ResponseBody
    public Object findQuery() {
        JSONArray jsonArray = esService.find(index, type);
        List<Article> articles = JSONArray.parseArray(jsonArray.toJSONString(), Article.class);
        System.out.println("articles.size = " + articles.size());
        return jsonArray;
    }

    @RequestMapping("/findPage")
    @ResponseBody
    public Object findPage(Integer pageSize, Integer pageNo) {
        JSONArray jsonArray = esService.findByPage(index, type, pageSize, pageNo);
        List<Article> articles = JSONArray.parseArray(jsonArray.toJSONString(), Article.class);
        System.out.println("articles.size = " + articles.size());
        return jsonArray;
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


    @RequestMapping("/createCluterName")
    @ResponseBody
    public Object createCluterName() {
        esService.createCluterName(indexTokenizer);
        return true;
    }

    @RequestMapping("/createMapping")
    @ResponseBody
    public Object createMapping() {
        esService.createMapping(indexTokenizer, typeTokenizer);
        return true;
    }

    @RequestMapping("/addTokenizer")
    @ResponseBody
    public String addTokenizer(String id) {
        Article article = articleService.findById(id);
        esService.save(indexTokenizer, typeTokenizer, article.getId(), JSON.parseObject(JSON.toJSONString(article)));
        return "zt";
    }

    //    @RequestMapping("/findTokenizer")
//    @ResponseBody
    public Object findTokenizer() {
        JSONArray jsonArray = esService.findTokenizer(indexTokenizer, "安信证券股份有限公司关于北");
        return jsonArray;
    }

}
