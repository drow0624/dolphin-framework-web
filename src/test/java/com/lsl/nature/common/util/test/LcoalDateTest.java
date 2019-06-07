package com.lsl.nature.common.util.test;

import com.alibaba.fastjson.JSON;
import com.lsl.nature.project.sysmgr.demo.domain.Article;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.Map;

public class LcoalDateTest {

    @Test
    public void test(){
        Article article = Article.createArticle();
        Map<String, Object> objectObjectHashMap = new HashMap<>();
        BeanUtils.copyProperties(article, objectObjectHashMap);
        System.out.println(JSON.toJSONString(article));


    }
}
