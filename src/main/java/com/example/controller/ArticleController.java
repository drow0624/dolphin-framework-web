package com.example.controller;

import com.example.entity.Article;
import com.example.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/articles")
@Slf4j
public class ArticleController {

    @Autowired
    private DataSource dataSource;


    @Resource
    private ArticleService articleService;

    @GetMapping
    public List<Article> findAll(){
     return  articleService.findAll();
    }
    @PostMapping
    public String add() throws SQLException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        log.info("Collecion hashCode {0},autoCommit {1}",System.identityHashCode(connection),connection.getAutoCommit());
        Article article = new Article();
        article.setName("清明");
//        article.setCreateTime(LocalDateTime.now());
        article.setContent(LocalDateTime.now().toString());
        article.setAuthor("李云"+System.nanoTime());
        articleService.add(article);
        log.info("Collecion hashCode {0},autoCommit {1}",System.identityHashCode(connection),connection.getAutoCommit());
        return "success!";
    }

    @PostMapping("/ex")
    public String addEx(){
        Article article = new Article();
        article.setName("清明");
//        article.setCreateTime(LocalDateTime.now());
        article.setContent(LocalDateTime.now().toString());
        article.setAuthor("李云"+System.nanoTime());
        articleService.add(article);
        article.setAuthor("李伟"+System.nanoTime());
        articleService.addEx(article);
        return "success!";
    }

}
