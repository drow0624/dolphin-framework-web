package priv.cy.project.sysmgr.demo.service;

import priv.cy.project.sysmgr.demo.domain.Article;
import priv.cy.project.sysmgr.demo.mapper.ArticleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Transactional 的事务开启 ，或者是基于接口的或者是基于类的代理被创建。
 * 所以在同一个类中一个方法调用另一个方法有事务的方法，事务是不会起作用的
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public List<Article> findAll() {
        return articleMapper.findAll();

    }

    @Override
    public void add(Article article) {
        articleMapper.add(article);
    }

    @Override
    public void addEx(Article article) {
//        DataSourceUtils.
        articleMapper.add(article);
        if(article!=null){
            throw new RuntimeException("Exception！");
        }
    }
}
