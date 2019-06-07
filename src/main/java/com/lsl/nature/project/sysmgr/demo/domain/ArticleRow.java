package com.lsl.nature.project.sysmgr.demo.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@ToString
public class ArticleRow extends BaseRowModel {

    @ExcelProperty(index = 0)
    private Long id;

    @ExcelProperty(index = 1)
    private String name;

    @ExcelProperty(index = 2)
    private String content;

    @ExcelProperty(index = 3)
    private String author;
    
    @ExcelProperty(index = 4, format = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;

}
