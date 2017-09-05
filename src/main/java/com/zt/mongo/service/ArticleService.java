package com.zt.mongo.service;

import com.zt.mongo.entity.Article;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ArticleService {

    List<Article> findList();
}
