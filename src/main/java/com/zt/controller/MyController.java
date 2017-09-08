package com.zt.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.zt.constants.SysConst;
import com.zt.entity.EnterpriseInfo;
import com.zt.entity.EnterpriseInv;
import com.zt.entity.Investmenter;
import com.zt.es.service.EsService;
import com.zt.mongo.entity.Article;
import com.zt.mongo.service.ArticleService;
import com.zt.utils.Utils;
import org.elasticsearch.index.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

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
        esService.save(SysConst.INDEX, SysConst.ARTICLE, article.getId(), JSON.parseObject(JSON.toJSONString(article)));
        return true;
    }

    @RequestMapping("/batchAdd")
    @ResponseBody
    public Object batchAdd() {
        List<Article> articles = articleService.findListByPageRtList(1, 100);
        esService.batchSave(SysConst.INDEX, SysConst.ARTICLE, articles);
        return true;
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public Object findAll() {
        JSONArray jsonArray = esService.find(SysConst.INDEX, SysConst.ARTICLE);
        List<Article> articles = JSONArray.parseArray(jsonArray.toJSONString(), Article.class);
        System.out.println("articles.size = " + articles.size());
        return jsonArray;
    }

    @RequestMapping("/findQuery")
    @ResponseBody
    public Object findQuery() {
        JSONArray jsonArray = esService.find(SysConst.INDEX, SysConst.ARTICLE);
        List<Article> articles = JSONArray.parseArray(jsonArray.toJSONString(), Article.class);
        System.out.println("articles.size = " + articles.size());
        return jsonArray;
    }

    @RequestMapping("/findPage")
    @ResponseBody
    public Object findPage(Integer pageSize, Integer pageNo) {
        JSONArray jsonArray = esService.findByPage(SysConst.INDEX, SysConst.ARTICLE, pageSize, pageNo);
        List<Article> articles = JSONArray.parseArray(jsonArray.toJSONString(), Article.class);
        System.out.println("articles.size = " + articles.size());
        return jsonArray;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(String id) {
        esService.delete(SysConst.INDEX, SysConst.ARTICLE, id);
        return true;
    }

    @RequestMapping("/update")
    @ResponseBody
    public Object update(String id) {
        esService.update(SysConst.INDEX, SysConst.ARTICLE, id);
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
        esService.createMapping(SysConst.INDEX, SysConst.ARTICLE);
        return true;
    }

    @RequestMapping("/saveEnterpriseInfo")
    @ResponseBody
    public Object saveEnterpriseInfo(@RequestParam("file") MultipartFile file, @RequestParam("encode") String encode) {
//        MultipartFile file = null;
        InputStream fis = null;
        try {
            fis = file.getInputStream();
            JSONArray jsonArray = Utils.readCsv(fis, encode);
            List<EnterpriseInv> enterpriseInvs = Lists.newArrayList();
            for (Iterator iterator = jsonArray.iterator(); iterator.hasNext(); ) {
                JSONArray ja = (JSONArray) iterator.next();
                String enterpriseName = ja.getString(0);
                String investmenter = ja.getString(1);
                String invType = ja.getString(2);
                EnterpriseInv ei = new EnterpriseInv(enterpriseName, investmenter, invType);
                enterpriseInvs.add(ei);
            }
            Map<String, Map<String, List<EnterpriseInv>>> map = enterpriseInvs.stream()
                    .collect(
                            groupingBy(EnterpriseInv::getEnterpriseName
                                    , groupingBy(EnterpriseInv::getInvType)));
            for (Map.Entry<String, Map<String, List<EnterpriseInv>>> entry : map.entrySet()) {
                String enterpriseName = entry.getKey();
                String enterpriseAbbr = getAbbreviation(enterpriseName);
                Map<String, List<EnterpriseInv>> val = entry.getValue();
                List<Investmenter> investmenters = Lists.newArrayList();
                for (Map.Entry<String, List<EnterpriseInv>> entry1 : val.entrySet()) {

                    String invType = entry1.getKey();
                    List<String> invList = entry1.getValue().stream()
                            .map(EnterpriseInv::getInvestmenter)
                            .collect(toList());
                    Investmenter investmenter = new Investmenter(invList, invType);
                    investmenters.add(investmenter);
                }

                EnterpriseInfo enterpriseInfo = new EnterpriseInfo(enterpriseName, enterpriseAbbr, investmenters);
                JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(enterpriseInfo));
                System.out.println(jsonObject);
                esService.save(SysConst.INDEX, SysConst.ENTERPRISEINFO, jsonObject);
            }


        } catch(IOException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @RequestMapping("/searchEnterpriseAbbr")
    @ResponseBody
    public Object searchEnterpriseAbbr(String enterpriseAbbr) {
        // Query
        FuzzyQueryBuilder fuzzy = QueryBuilders.fuzzyQuery("enterpriseAbbr.keyword", enterpriseAbbr);
        // 最大编辑距离
//        fuzzy.fuzziness(Fuzziness.ONE);
        // 公共前缀
//        fuzzy.prefixLength(0);
//        WildcardQueryBuilder wildcard = QueryBuilders.wildcardQuery("enterpriseAbbr", "*" + enterpriseAbbr + "*");
        WildcardQueryBuilder wildcard = QueryBuilders.wildcardQuery("enterpriseAbbr.keyword", "*招商*");
        TermQueryBuilder termQuery = QueryBuilders.termQuery("enterpriseAbbr.keyword", enterpriseAbbr);
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("enterpriseAbbr.keyword", enterpriseAbbr);
        JSONArray result = esService.find(SysConst.INDEX, SysConst.ENTERPRISEINFO, wildcard);
        return result;
    }

    public static String getAbbreviation(String enterpriseName) {
        String result = null;
        if (enterpriseName.endsWith("有限责任公司")) {
            result = enterpriseName.substring(0, enterpriseName.lastIndexOf("有限责任公司"));
        } else if (enterpriseName.endsWith("股份有限公司")) {
            result = enterpriseName.substring(0, enterpriseName.lastIndexOf("股份有限公司"));
        } else if (enterpriseName.endsWith("有限公司")) {
            result = enterpriseName.substring(0, enterpriseName.lastIndexOf("有限公司"));
        }
        return result;
    }

}
