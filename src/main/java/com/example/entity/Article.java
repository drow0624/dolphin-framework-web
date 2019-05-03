package com.example.entity;

import com.example.annotion.DBColumn;
import com.example.annotion.DBTable;
import com.example.excel.article.ArticleRow;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.util.Date;

@Data
@DBTable("article")
public class Article extends Document{

    @DBColumn("id")
    private Long id;

    @DBColumn("name")
    private String name;

    @DBColumn("content")
    private String content;

    @DBColumn("author")
    private String author;

    @DBColumn("create_time")
    private Date createTime;

    public Article from(ArticleRow row){
        this.name = row.getName();
        this.author = row.getAuthor();
        this.content = row.getContent();
//        this.createTime = LocalDateTime.now().getLong(ChronoField.);
        return this;
    }
}
