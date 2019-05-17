package priv.cy.common.es;

import priv.cy.common.es.annotation.FieldType;
import priv.cy.common.es.annotation.DateFormat;
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
