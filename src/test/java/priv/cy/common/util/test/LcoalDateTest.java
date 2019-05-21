package priv.cy.common.util.test;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import priv.cy.project.sysmgr.demo.domain.Article;

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
