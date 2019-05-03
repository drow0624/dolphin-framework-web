package com.example.mapper.mapper;


import com.alibaba.fastjson.JSON;
import com.example.Application;
import com.example.entity.Article;
import com.example.mapper.CommonMapper;
import com.example.mapper.condition.QueryFilter;
import com.example.mapper.domain.Table;
import com.example.util.DBReflectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class QueryFilterTest {

    @Resource
    private CommonMapper commonMapper;

    @Test
    public void query(){
        QueryFilter filter = new QueryFilter(Table.ARTICLE);
        List<Map<String, Object>> selects = commonMapper.select(filter);
        log.info(JSON.toJSONString(selects));
        List<Article> articles = new ArrayList<>();
        for (Map<String, Object> select : selects) {
            Article article = DBReflectionUtil.copyProperties(select, Article.class);
            articles.add(article);
        }
        log.info(JSON.toJSONString(articles));

    }

}
