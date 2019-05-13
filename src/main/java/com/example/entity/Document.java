package com.example.entity;

import com.example.annotion.DBColumn;
import com.example.es.annotation.ESField;
import com.example.es.annotation.FieldType;
import lombok.Data;

@Data
public class Document {

    @DBColumn("keywords")
    @ESField(type= FieldType.KEYWORD)
    private String keywords;

    @DBColumn("hy_url")
    @ESField(type= FieldType.TEXT)
    private String url;

    @DBColumn("comment")
    @ESField(type= FieldType.TEXT)
    private BaseEntity text;

}
