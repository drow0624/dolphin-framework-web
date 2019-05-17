package com.company.project.sysmgr.demo.domain;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ArticleExcelListener extends AnalysisEventListener<ArticleRow> {

    private List<ArticleRow> articleRows = new ArrayList<>();

    @Override
    public void invoke(ArticleRow articele, AnalysisContext context) {
        //处理每一行
        articleRows.add(articele);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        this.handData();
    }

    private void handData() {
        for (ArticleRow articele : articleRows) {
            log.info(articele.toString());
        }
    }
}
