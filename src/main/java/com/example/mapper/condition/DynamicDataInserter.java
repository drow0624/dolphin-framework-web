package com.example.mapper.condition;

import com.example.mapper.domain.DBField;
import com.example.mapper.domain.InsertColumn;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:支持对 无实体类 的数据 进行insert操作  <BR>
 * 动态数据插入器
 */
@Slf4j
public class DynamicDataInserter {

    @Getter
    private List<String> fields = new ArrayList<>();

    @Getter
    private List<Object> values = new ArrayList<>();

    @Getter
    private String tableName;

    /**
     * 元数据里面的字段名集合
     */
    @Getter
    private List<String> fieldNames;

    /**
     * 不推荐使用，因为无法验证查询条件对应的字段的真实性
     *
     * @param tableName 表名
     */
    public DynamicDataInserter(String tableName) {
        if (StringUtils.isEmpty(tableName)) {
            log.error("invalid tableName: 传入的参数tableName（表名）为null");
            throw new IllegalArgumentException("invalid tableName");
        }
        this.tableName = tableName;
    }

    public DynamicDataInserter(String tableName, List fieldObject) {
        if (fieldObject == null || fieldObject.isEmpty()) {
            log.error("invalid fieldObject: 传入的参数fieldObject（元数据字段信息）为null");
            throw new IllegalArgumentException("invalid fieldObject");
        }

        if (StringUtils.isEmpty(tableName)) {
            log.error("invalid tableName: 传入的参数tableName（表名）为null");
            throw new IllegalArgumentException("invalid tableName");
        }
        this.tableName = tableName;

        this.setFieldNames(fieldObject);
    }

    private void setFieldNames(List<Object> fieldList) {
        fieldNames = new ArrayList<>();

        if (fieldList.get(0) instanceof InsertColumn) {
            this.setMetadataFieldNames(fieldList);
        }
    }

    private void setMetadataFieldNames(List<Object> fieldList) {
        for (Object objectField : fieldList) {
            InsertColumn field = (InsertColumn) objectField;
            fieldNames.add(field.getColumnName());
        }
    }

    public void addCond(String fieldName, Object value) {
        if (StringUtils.isEmpty(fieldName) || value == null) {
            return;
        }

        this.addCond(new DBField(fieldName, value));
    }

    /**
     * 添加一个查询条件
     *
     * @param field 字段对象
     * @return CondDBField
     */
    public void addCond(DBField field) {
        this.checkFieldName(field.getName());
        fields.add(field.getName());
        values.add(field.getValue());
    }

    private void checkFieldName(String fieldName) {
        if (fieldNames == null) {
            // 这种情况是 直接通过tablename 创建的对象，fieldNames 没有校验的数据
            return;
        }
        if (!fieldNames.contains(fieldName)) {
            log.error("invalid field name: 没有当前表并且fieldName与CATEGORY_ID、PARENT_ID常量也不相等", new RuntimeException());
            throw new IllegalArgumentException("invalid field name " + fieldName + " of table " + tableName);
        }
    }
}
