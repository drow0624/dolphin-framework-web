package com.lsl.nature.common.es;

import com.lsl.nature.common.es.annotation.DateFormat;
import com.lsl.nature.common.es.annotation.FieldType;
import lombok.*;

@Data
@Builder
@ToString
public class ESMappingDefinition {

    private String fieldName;

    private String defaultFieldType;

    private FieldType type;

    private boolean index;

    private String pattern;

    private boolean store;

    private String searchAnalyzer;

    private String analyzer;

    private String normalizer;

    private String[] ignoreFields;

    private DateFormat dateFormat;
}
