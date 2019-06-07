package com.lsl.nature.common.db.mapper.condition;

import priv.cy.common.db.mapper.pojo.CondDBField;
import priv.cy.common.db.mapper.pojo.DBField;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 支持对 无实体类 的数据 进行更新操作 <br>
 * 动态数据更新器
 */
public class DynamicDataUpdater /*extends DynamicQueryFilter*/ {

    @Getter
    private String tableName;

    @Getter
    private List<DBField> fields;

    @Getter
    private DynamicQueryFilter queryFilter;

    public DynamicDataUpdater(String tableName) {
        this.tableName = tableName;
        this.queryFilter = new DynamicQueryFilter(tableName);
    }

    public void addUpdateField(String filedName, Object value) {
        this.addUpdateField(new DBField(filedName, value));
    }


    public void addUpdateField(DBField field) {
        if (field == null || field.getValue() == null) {
            return;
        }

        if (fields == null) {
            fields = new ArrayList<>();
        }

        fields.add(field);
    }

    public void addQueryFilterField(CondDBField field) {
        queryFilter.addCond(field);
    }

    public void addQueryFilterField(String filedName, Object value) {
        queryFilter.addCond(filedName, value);
    }


}
