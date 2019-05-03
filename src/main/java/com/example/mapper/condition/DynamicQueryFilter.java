package com.example.mapper.condition;

import com.example.mapper.domain.*;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 为适配动态表查询 ，参照 QueryFilter 重写的， 功能 及 用法与QueryFilter 类似<BR>
 */

@Slf4j
public class DynamicQueryFilter {

    @Getter
    private List<CondDBField> condFields;

    @Getter
    private String tableName;

    /**
     * 元数据里面的字段名集合
     */
    @Getter
    private List<String> fieldNames;

    /**
     * 选择需要返回的字段 ，当无值时，默认 SELECT * FROM TABLE
     */
    @Setter
    @Getter
    private String selector;

    private static final String OR_COMPLEX_FIELD_NAME = "OR_COMPLEX_FIELD";

    // 排序字段
    @Getter
    private List<SortDBField> sortFields;

    // 分组字段
    @Getter
    private List<String> groupFields;

    // 分页
    @Getter
    private DBPage pager;

    /**
     * 不推荐使用，因为无法验证查询条件对应的字段的真实性
     *
     * @param tableName 表名
     */
    public DynamicQueryFilter(String tableName) {
        if (StringUtils.isEmpty(tableName)) {
            log.error("invalid tableName: 传入的参数tableName（表名）为null");
            throw new IllegalArgumentException("invalid tableName");
        }
        this.tableName = tableName;
        fieldNames = new ArrayList<>();
    }

    public DynamicQueryFilter(String tableName, List fieldObject) {
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

    public CondDBField addOrConds(OrCondDBFields cond) {
        return addCond(OR_COMPLEX_FIELD_NAME, cond);
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


    public CondDBField addCond(String fieldName, Object value) {
        if (StringUtils.isEmpty(fieldName) || value == null) {
            return null;
        }

        return addCond(new CondDBField(fieldName, value));
    }

    /**
     * 添加一个查询条件
     *
     * @param field 字段对象
     * @return CondDBField
     */
    public CondDBField addCond(CondDBField field) {
        if (field == null) {
            return null;
        }

        if (OR_COMPLEX_FIELD_NAME.equalsIgnoreCase(field.getFieldName())) {
            if (field.getCondValue() instanceof OrCondDBFields) {
                OrCondDBFields orCondDBFields = (OrCondDBFields) field.getCondValue();
                for (CondDBField condDBField : orCondDBFields.getFields()) {
                    this.add(condDBField.getFieldName());
                }
            }
        } else {
            this.add(field.getFieldName());
        }
        //是否有必要？
//        this.checkFieldName(field.getFieldName());

        if (condFields == null) {
            condFields = new ArrayList<>();
        }
        condFields.add(field);

        return field;
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

    public boolean hasCondFields() {
        return this.getCondFields() != null && !this.getCondFields().isEmpty();
    }

    /**
     * 添加字段名到  fieldNames <br>
     * 原因：为了能使用 有实体类的commonMapper.xml 文件
     *
     * @param fieldName 被添加的字段名
     */
    private void add(String fieldName) {
        if (!fieldNames.contains(fieldName)) {
            fieldNames.add(fieldName);
        }
    }

    /**
     * 添加一个排序字段
     *
     * @param filedName 字段名
     * @param isAsc     是否升序  true： 升序排序
     */
    public void addSortField(String filedName, boolean isAsc) {
        if (StringUtils.isEmpty(filedName)) {
            return;
        }

        this.addSortField(new SortDBField(filedName.trim(), isAsc));
    }

    /**
     * 添加一个排序字段对象
     *
     * @param sortField SortDBField
     */
    private void addSortField(SortDBField sortField) {
        if (sortField == null) {
            return;
        }

        // 是否有必要？
//        checkFieldName(sortField.getFieldName());

        if (sortFields == null) {
            sortFields = new ArrayList<>();
        }

        this.add(sortField.getFieldName());

        sortFields.add(sortField);
    }

    public void removeAllSortField(){
        if (this.sortFields != null) {
            this.sortFields = null;
        }
    }

    /**
     * 由外面的分页转换为数据库分页
     *
     * @param pager Page
     */
    public void setPager(Page pager) {
        if (pager != null) {
            this.pager = new DBPage((pager.getCurrPage() - 1) * pager.getPageSize(), pager.getPageSize());
        }
    }

    /**
     * 设置分页
     *
     * @param pager DBPage
     */
    public void setPager(DBPage pager) {
        this.pager = pager;
    }

    /**
     * 添加分组字段
     *
     * @param fieldName 字段名
     */
    public void addGroupField(String fieldName) {
        if (StringUtils.isEmpty(fieldName)) {
            return;
        }
        checkFieldName(fieldName);

        if (this.groupFields == null) {
            this.groupFields = new ArrayList<>();
        }

        this.add(fieldName);

        this.groupFields.add(fieldName);
    }

    /**
     * 删除某个字段的查询条件
     *
     * @param fieldName
     * @return
     */
    public boolean removeCond(String fieldName) {
        if (StringUtils.isEmpty(fieldName)) {
            return false;
        }

        boolean isRemoved = false;
        for (int index = condFields.size() - 1; index >= 0; index--) {
            if (condFields.get(index).getFieldName().equals(fieldName)) {
                condFields.remove(index);
                isRemoved = true;
            }
        }

        return isRemoved;
    }
}
