package com.example.mapper.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class DBField {

    private String name;

    private Object value;

    public DBField(String fieldName, Object value) {
        this.name = fieldName;
        this.value = value;
    }
}
