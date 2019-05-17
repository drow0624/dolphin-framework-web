package com.company.common.db.mapper.pojo;

import com.company.common.db.annotation.DBColumn;
import com.company.common.db.annotation.DBTable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DBRow {

    @Getter
    @Setter
    private Integer id;

    @Getter
    private String tableName;

    @Getter
    private List<String> fields = new ArrayList<>();

    @Getter
    private List<Object> values = new ArrayList<>();

    private DBRow(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 添加一个字段
     *
     * @param fieldName 字段名
     * @param value     字段值
     */
    public void addField(String fieldName, Object value) {
        fields.add(fieldName);
        values.add(value);
    }

    /**
     * 转换数据库po为dbRow
     *
     * @param po
     * @return
     */
    public static DBRow toRow(Object po){
        Class<?> clazz = po.getClass();
        if (!clazz.isAnnotationPresent(DBTable.class)) {
            throw new RuntimeException("实体类所注解的表名未找到");
        }

        DBRow row = new DBRow(clazz.getAnnotation(DBTable.class).value());
        do {
            row = getFields(po, row, clazz);
            clazz = clazz.getSuperclass();
        } while (clazz != Object.class);

        return row;
    }

    private static DBRow getFields(Object po, DBRow row, Class<?> clazz) {

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(DBColumn.class)
                    || field.getAnnotation(DBColumn.class).autoInc()) {
                continue;
            }
            // 获取db字段名
            DBColumn dbField = field.getAnnotation(DBColumn.class);
            String fieldName;
            if (StringUtils.isEmpty(dbField.value())) {
                // 直接使用field的名字作为字段名
                fieldName = field.getName();
            } else {
                fieldName = dbField.value();
            }
            // 获取值
            try {
                field.setAccessible(true);
                Object value = field.get(po);
                if (value != null) {
                    row.addField(fieldName, value);
                }
            } catch (IllegalAccessException e) {
                log.error("", e);
            }
        }
        return row;
    }
}
