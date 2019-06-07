package com.lsl.nature.common.db.mapper.pojo;

import com.lsl.nature.common.util.DBReflectionUtil;
import com.lsl.nature.project.sysmgr.demo.domain.Article;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.List;

public enum Table {

    /**
     * 数据表枚举定义
     */
    ARTICLE("article", Article.class);

    // 表名
    @Getter
    private final String tableName;

    // 表中字段
    @Getter
    private final List<String> fields;


    Table(String tableName, Class<?>... poClass) {
        this.tableName = tableName;
        this.fields = DBReflectionUtil.allDBFieldNames(poClass);
    }

    /**
     * 表是否包含指定字段
     *
     * @param fieldName 字段名
     * @return true : 包含
     */
    public boolean containsField(String fieldName) {
        if (StringUtils.isEmpty(fieldName)) {
            return false;
        }

        for (String field : fields) {
            if (field.equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据表名获取表
     *
     * @param tableName 表名
     * @return Table
     */
    public static Table of(String tableName) {

        if (StringUtils.isEmpty(tableName)) {
            return null;
        }
        Table[] tables = Table.values();
        for (Table table : tables) {
            if (table.getTableName().equalsIgnoreCase(tableName)) {
                return table;
            }
        }
        return null;
    }

}
