package com.lsl.nature.common.db.mapper.pojo;

import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class SortDBField {

    private String fieldName;

    private boolean isAsc = true;

    public SortDBField(String fieldName){
        this(fieldName, true);
    }
    public SortDBField(String fieldName, boolean isAsc) {
        if (StringUtils.isEmpty(fieldName)) {
            throw new IllegalArgumentException("empty field");
        }
        this.fieldName = fieldName.trim();
        this.isAsc = isAsc;
    }
}
