package com.example.service;

import com.example.entity.Article;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArticleService {
    List<Article> findAll();
    void add(Article article);
    void addEx(Article article);
}
