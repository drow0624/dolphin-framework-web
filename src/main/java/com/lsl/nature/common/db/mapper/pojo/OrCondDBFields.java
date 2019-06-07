package com.lsl.nature.common.db.mapper.pojo;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrCondDBFields {

    private List<CondDBField> fields;

    /**
     * 添加一个条件字段
     *
     * @param fieldName
     * @param value
     * @return
     */
    public CondDBField addCond(String fieldName, Object value) {
        if (StringUtils.isEmpty(fieldName) || value == null) {
            return null;
        }
        return addCond(new CondDBField(fieldName, value));
    }

    /**
     * 添加一个条件字段
     *
     * @param field
     * @return
     */
    public CondDBField addCond(CondDBField field) {
        if (field == null) {
            return null;
        }

        if (fields == null) {
            fields = new ArrayList<>();
        }
        fields.add(field);
        return field;
    }

}
