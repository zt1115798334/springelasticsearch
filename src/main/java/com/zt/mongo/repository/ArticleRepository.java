package com.zt.mongo.repository;

import com.zt.mongo.entity.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ArticleRepository extends MongoRepository<Article, String> {
}
