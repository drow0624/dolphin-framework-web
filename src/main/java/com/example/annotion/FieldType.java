package com.example.annotion;

public enum FieldType {
    Text,
    Integer,
    Long,
    Date,
    Float,
    Double,
    Boolean,
    Object,
    Auto,
    Nested,
    Ip,
    Attachment,
    Keyword;

    private FieldType() {
    }

    public static FieldType of(String value){
        FieldType[] values = FieldType.values();
        for (FieldType fieldType : values) {
            if(value!=null&&value.equalsIgnoreCase(fieldType.toString())){
                return fieldType;
            }
        }
        return FieldType.Text;
    }
}
