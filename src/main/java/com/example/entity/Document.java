package com.example.entity;

import com.example.annotion.DBColumn;
import com.example.annotion.ESField;
import com.example.annotion.FieldType;
import lombok.Data;

@Data
public class Document {

    @DBColumn("keywords")
    @ESField(type= FieldType.Keyword)
    private String keywords;

    @DBColumn("hy_url")
    @ESField(type= FieldType.Text)
    private String url;

    @DBColumn("comment")
    @ESField(type= FieldType.Text)
    private BaseEntity text;

}
