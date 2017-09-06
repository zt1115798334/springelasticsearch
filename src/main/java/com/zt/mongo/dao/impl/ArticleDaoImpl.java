package com.zt.mongo.dao.impl;


import com.zt.base.entity.PageResult;
import com.zt.mongo.dao.ArticleDao;
import com.zt.mongo.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleDaoImpl implements ArticleDao {
    @Autowired
    protected MongoTemplate mongoTemplate;


    @Override
    public PageResult<Article> findListByPage(Integer pageNumber, Integer pageSize) {
        int skip = (pageNumber - 1) * pageSize;
        Query query = new Query();
        long total = mongoTemplate.count(query, Article.class);
        query.skip(skip);
        query.limit(pageSize);
        List<Article> list = mongoTemplate.find(query, Article.class);
        long totalPages = (total + Long.valueOf(pageSize - 1)) / Long.valueOf(pageSize);
        PageResult<Article> pageResult = new PageResult<Article>(
                list, totalPages, total, list.size());

        return pageResult;
    }

    @Override
    public List<Article> findListByPageRtList(Integer pageNumber, Integer pageSize) {
        int skip = (pageNumber - 1) * pageSize;
        Query query = new Query();
        query.skip(skip);
        query.limit(pageSize);
        List<Article> list = mongoTemplate.find(query, Article.class);
        return list;
    }
}
