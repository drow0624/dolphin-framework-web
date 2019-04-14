package com.example.controller;

import com.example.entity.Article;
import com.example.service.ArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @GetMapping
    public List<Article> findAll(){
     return  articleService.findAll();
    }
    @PostMapping
    public String add(){
        Article article = new Article();
        article.setName("清明");
        article.setCreateTime(LocalDateTime.now());
        article.setContent(LocalDateTime.now().toString());
        article.setAuthor("李云"+System.nanoTime());
        articleService.add(article);
        return "success!";
    }

    @PostMapping("/ex")
    public String addEx(){
        Article article = new Article();
        article.setName("清明");
        article.setCreateTime(LocalDateTime.now());
        article.setContent(LocalDateTime.now().toString());
        article.setAuthor("李云"+System.nanoTime());
        articleService.add(article);
        article.setAuthor("李伟"+System.nanoTime());
        articleService.addEx(article);
        return "success!";
    }
}
