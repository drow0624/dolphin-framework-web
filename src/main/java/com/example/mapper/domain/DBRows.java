package com.example.mapper.domain;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DBRows {

    public static <T> List<DBRow> toRows(List<T> rows) {

        List<DBRow> dbRows = null;

        if(rows != null && !rows.isEmpty()) {

            dbRows = new ArrayList<>();

            for (T t : rows) {
                dbRows.add(DBRow.toRow(t));
            }
        }
        return dbRows;
    }

    private DBRows(){
        super();
    }

}
