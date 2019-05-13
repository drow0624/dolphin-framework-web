package com.example.annotion;

public enum FieldType {
    TEXT,
    INTEGER,
    LONG,
    DATE,
    FLOAT,
    DOUBLE,
    BOOLEAN,
    OBJECT,
    AUTO,
    NESTED,
    IP,
    ATTACHMENT,
    KEYWORD;

    private FieldType() {
    }
    public static FieldType of(String value){
        FieldType[] values = FieldType.values();
        for (FieldType fieldType : values) {
            if(value!=null&&value.equalsIgnoreCase(fieldType.toString())){
                return fieldType;
            }
        }
        return FieldType.TEXT;
    }
}
