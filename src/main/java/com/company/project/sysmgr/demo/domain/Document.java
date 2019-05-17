package com.company.project.sysmgr.demo.domain;

import com.company.common.es.annotation.FieldType;
import com.company.common.db.annotation.DBColumn;
import com.company.common.es.annotation.ESField;
import com.company.project.common.domain.BaseEntity;
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
