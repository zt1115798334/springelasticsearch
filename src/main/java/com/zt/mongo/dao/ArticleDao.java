package com.zt.mongo.dao;

import com.zt.base.entity.PageResult;
import com.zt.mongo.entity.Article;

public interface ArticleDao {
    PageResult<Article> findListByPage(Integer pageNumber, Integer pageSize);
}
