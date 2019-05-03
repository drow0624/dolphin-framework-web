package com.example.mapper.domain;

import lombok.Data;
import lombok.Getter;

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
