package com.example.mapper.mapper;


import com.alibaba.fastjson.JSON;
import priv.cy.Application;
import priv.cy.project.sysmgr.demo.domain.Article;
import priv.cy.common.db.mapper.CommonMapper;
import priv.cy.common.db.mapper.condition.QueryFilter;
import priv.cy.common.db.mapper.pojo.Table;
import priv.cy.common.util.DBReflectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
