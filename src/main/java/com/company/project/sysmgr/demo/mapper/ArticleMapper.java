package com.company.project.sysmgr.demo.mapper;

import com.company.project.sysmgr.demo.domain.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper {
    List<Article> findAll();
    void add(@Param("article") Article article);
}
