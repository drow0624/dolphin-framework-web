package com.example.entity;

import com.example.annotion.DBColumn;
import com.example.annotion.ESField;
import com.example.annotion.FieldType;
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
