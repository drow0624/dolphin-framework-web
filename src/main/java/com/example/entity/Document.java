package com.example.entity;

import com.example.annotion.DBColumn;
import lombok.Data;

@Data
public class Document {

    @DBColumn("keywords")
    private String keywords;

    @DBColumn("hy_url")
    private String url;

    @DBColumn("comment")
    private BaseEntity text;

}
