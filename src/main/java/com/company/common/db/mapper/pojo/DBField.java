package com.company.common.db.mapper.pojo;

import lombok.Data;

@Data
public class DBField {

    private String name;

    private Object value;

    public DBField(String fieldName, Object value) {
        this.name = fieldName;
        this.value = value;
    }
}
