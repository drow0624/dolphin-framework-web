package com.example.mapper.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页数据的返回模板
 */
@Data
public class Page {

    private Integer currPage;

    private Integer pageSize;

    private Long pageCount;

    private Long itemCount;

    public Page(){}

    public Page(Integer currPage, Integer pageSize, Long pageCount, Long itemCount){
        this.currPage = currPage;
        this.pageSize = pageSize;
        this.pageCount = pageCount;
        this.itemCount = itemCount;
    }
}
