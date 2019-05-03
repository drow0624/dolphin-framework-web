package com.example.mapper.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class QueryDBField {
    private String name;

    private String sort;

    private String searchText;
}
