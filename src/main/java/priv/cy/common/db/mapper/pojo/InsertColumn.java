package priv.cy.common.db.mapper.pojo;

import lombok.Data;

/**
 * 保存表字段值
 */
@Data
public class InsertColumn {

    private String columnName;

    private Object columnValue;

    private Integer isUnique;

    private String fieldType;

    private int isTitle;

    /**
     * 字段中文名称
     */
    private String cnName;

    public InsertColumn() {
    }

    public InsertColumn(String columnName, Object columnValue) {
        this.columnName = columnName;
        this.columnValue = columnValue;
    }


    public boolean isTitle() {
        boolean isTitle0= false;
        if (this.isTitle == 1) {
            isTitle0= true;
        }
        return isTitle0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InsertColumn{");
        sb.append("columnName='").append(columnName).append('\'');
        sb.append(", columnValue=").append(columnValue);
        sb.append(", isUnique=").append(isUnique);
        sb.append(", fieldType='").append(fieldType).append('\'');
        sb.append(", isTitle=").append(isTitle);
        sb.append(", cnName='").append(cnName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
