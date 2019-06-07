package com.lsl.nature.project.sysmgr.demo.service;


import com.lsl.nature.project.sysmgr.demo.domain.Article;

import java.util.List;

public interface ArticleService {
    List<Article> findAll();
    void add(Article article);
    void addEx(Article article);
}
