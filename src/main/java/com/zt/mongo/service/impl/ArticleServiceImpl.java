package com.zt.mongo.service.impl;

import com.zt.mongo.dao.ArticleDaoImpl;
import com.zt.mongo.entity.Article;
import com.zt.mongo.repository.ArticleRepository;
import com.zt.mongo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDaoImpl articleDao;

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> findList() {
//        List<Article> list = articleRepository.findAll();
        long count = articleRepository.count();
        System.out.println("count = " + count);
        return null;
    }
}
