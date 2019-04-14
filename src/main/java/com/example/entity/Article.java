package com.example.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Article {
    private Long id;
    private String name;
    private String content;
    private String author;
    private LocalDateTime createTime;
}
