package com.zt.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zt.constants.SysConst;
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


    @Autowired
    private ArticleService articleService;

    @Autowired
    private EsService esService;

    @RequestMapping("/test")
    @ResponseBody
    public Object test() {
        esService.test();
        return true;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Object add(String id) {
        Article article = articleService.findById(id);
        esService.save(SysConst.INDEX, SysConst.TYPE, article.getId(), JSON.parseObject(JSON.toJSONString(article)));
        return true;
    }

    @RequestMapping("/batchAdd")
    @ResponseBody
    public Object batchAdd() {
        List<Article> articles = articleService.findListByPageRtList(1, 100);
        esService.batchSave(SysConst.INDEX, SysConst.TYPE, articles);
        return true;
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public Object findAll() {
        JSONArray jsonArray = esService.find(SysConst.INDEX, SysConst.TYPE);
        List<Article> articles = JSONArray.parseArray(jsonArray.toJSONString(), Article.class);
        System.out.println("articles.size = " + articles.size());
        return jsonArray;
    }

    @RequestMapping("/findQuery")
    @ResponseBody
    public Object findQuery() {
        JSONArray jsonArray = esService.find(SysConst.INDEX, SysConst.TYPE);
        List<Article> articles = JSONArray.parseArray(jsonArray.toJSONString(), Article.class);
        System.out.println("articles.size = " + articles.size());
        return jsonArray;
    }

    @RequestMapping("/findPage")
    @ResponseBody
    public Object findPage(Integer pageSize, Integer pageNo) {
        JSONArray jsonArray = esService.findByPage(SysConst.INDEX, SysConst.TYPE, pageSize, pageNo);
        List<Article> articles = JSONArray.parseArray(jsonArray.toJSONString(), Article.class);
        System.out.println("articles.size = " + articles.size());
        return jsonArray;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(String id) {
        esService.delete(SysConst.INDEX, SysConst.TYPE, id);
        return true;
    }

    @RequestMapping("/update")
    @ResponseBody
    public Object update(String id) {
        esService.update(SysConst.INDEX, SysConst.TYPE, id);
        return true;
    }


    @RequestMapping("/createCluterName")
    @ResponseBody
    public Object createCluterName() {
        esService.createCluterName(SysConst.INDEX);
        return true;
    }

    @RequestMapping("/createMapping")
    @ResponseBody
    public Object createMapping() {
        esService.createMapping(SysConst.INDEX, SysConst.TYPE);
        return true;
    }

    //    @RequestMapping("/findTokenizer")
//    @ResponseBody
    public Object findTokenizer() {
        JSONArray jsonArray = esService.findTokenizer(SysConst.INDEX, "安信证券股份有限公司关于北");
        return jsonArray;
    }

}
