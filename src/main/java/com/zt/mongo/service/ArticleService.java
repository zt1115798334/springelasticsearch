package com.zt.mongo.service;

import com.zt.base.entity.PageResult;
import com.zt.mongo.entity.Article;

import java.util.List;

public interface ArticleService {

    List<Article> findList();

    Article findById(String id);

    PageResult<Article> findListByPage(Integer pageNumber, Integer pageSize);

    List<Article> findListByPageRtList(Integer pageNumber, Integer pageSize);
}
