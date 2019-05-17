package priv.cy.common.db.mapper.pojo;

import priv.cy.common.db.mapper.condition.QueryFilter;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据更新
 */
@Data
public class DBUpdater {

    private String tableName;

    private List<DBField> fields;

    private QueryFilter queryFilter;

    public DBUpdater(String tableName) {
        this.tableName = tableName;
    }

    public DBUpdater(Table table) {
        this.tableName = table.getTableName();
        queryFilter = new QueryFilter(table);
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

    public void addUpdateField(String filedName, Object value) {
        this.addUpdateField(new DBField(filedName, value));
    }

    public void addQueryFilterField(CondDBField field) {
        queryFilter.addCond(field);
    }

    public void addQueryFilterField(String filedName, Object value) {
        queryFilter.addCond(filedName, value);
    }

}
