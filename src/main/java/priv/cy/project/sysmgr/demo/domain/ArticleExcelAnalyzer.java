package priv.cy.project.sysmgr.demo.domain;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class ArticleExcelAnalyzer {

    public static void read(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(resource.getFile()));
        ArticleExcelListener excelListener = new ArticleExcelListener();
        EasyExcelFactory.readBySax(inputStream,
                new Sheet(1, 1, ArticleRow.class),
                excelListener);
        inputStream.close();
    }

    public static void main(String[] args) throws Exception {
        ArticleExcelAnalyzer.read("/Article.xlsx");
    }



}