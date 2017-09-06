package com.zt.mongo.service.impl;

import com.zt.base.entity.PageResult;
import com.zt.mongo.dao.ArticleDao;
import com.zt.mongo.entity.Article;
import com.zt.mongo.repository.ArticleRepository;
import com.zt.mongo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public List<Article> findList() {
        long count = articleRepository.count();
        System.out.println("count = " + count);
        return articleRepository.findAll();
    }

    @Override
    public Article findById(String id) {
        return articleRepository.findOne(id);
    }


    @Override
    public PageResult<Article> findListByPage(Integer pageNumber, Integer pageSize) {
        return articleDao.findListByPage(pageNumber, pageSize);
    }

    @Override
    public List<Article> findListByPageRtList(Integer pageNumber, Integer pageSize) {
        return articleDao.findListByPageRtList(pageNumber, pageSize);
    }
}
