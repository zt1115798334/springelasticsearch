package com.zt.mongo.dao;


import com.zt.mongo.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleDaoImpl {
    @Autowired
    protected MongoTemplate mongoTemplate;

    public List<Article> findList() {
        Query query = new Query();
        List<Article> articles = mongoTemplate.find(query, Article.class);
        return articles;
    }
}
