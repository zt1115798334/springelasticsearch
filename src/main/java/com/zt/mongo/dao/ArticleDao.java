package com.zt.mongo.dao;

import com.zt.base.entity.PageResult;
import com.zt.mongo.entity.Article;

import java.util.List;

public interface ArticleDao {
    PageResult<Article> findListByPage(Integer pageNumber, Integer pageSize);

    List<Article> findListByPageRtList(Integer pageNumber, Integer pageSize);
}
