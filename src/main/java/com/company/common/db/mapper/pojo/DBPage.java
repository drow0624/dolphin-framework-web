package com.company.common.db.mapper.pojo;

import lombok.Data;

@Data
public class DBPage {

    private final int offset;

    private final int count;

    public DBPage(int offset, int count) {
        if (offset < 0 || count < 0) {
            throw new IllegalArgumentException("offset or count must be equal or greater than 0");
        }
        this.offset = offset;
        this.count = count;
    }
}
