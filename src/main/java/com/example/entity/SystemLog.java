package com.example.entity;


import com.example.es.annotation.ESDocument;
import com.example.es.annotation.ESField;
import com.example.es.annotation.FieldType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ESDocument(indexName = "uirb_log_index",type = "uirb_system_log")
public class SystemLog {

    @ESField(type= FieldType.TEXT)
    private String logUser;

    @ESField(type= FieldType.TEXT)
    private String trueName;

    @ESField(type= FieldType.TEXT)
    private String operateType;

    @ESField(type= FieldType.TEXT)
    private String logTime;

    @ESField(type= FieldType.TEXT)
    private String logUserIP;

    @ESField(type= FieldType.TEXT)
    private String objName;

    @ESField(type= FieldType.TEXT)
    private String logDesc;

    @ESField(type= FieldType.TEXT)
    private String timeUsed;

    @ESField(type= FieldType.TEXT)
    private String taskTitle;

    @ESField(type= FieldType.TEXT)
    private String taskStatus;

    @ESField(type= FieldType.TEXT)
    private String logUserGroup;

    @ESField(type= FieldType.TEXT)
    private String logResult;

    @ESField(type= FieldType.TEXT)
    private String errorType;

    @ESField(type= FieldType.TEXT)
    private String warnType;

    @ESField(type= FieldType.TEXT)
    private String debugType;

    @ESField(type= FieldType.TEXT)
    private String infoType;

    @ESField(type= FieldType.TEXT)
    private String logLevel;

    @ESField(type= FieldType.TEXT)
    private String classInfo;

    @ESField(type= FieldType.TEXT)
    private String module;

    @ESField(type= FieldType.TEXT)
    private String machineIP;

    @ESField(type= FieldType.TEXT)
    private String siteId;

    @ESField(type= FieldType.KEYWORD)
    private String moduleName;

    @ESField(type= FieldType.TEXT)
    private String type;

    @ESField(type= FieldType.TEXT)
    private String textValue;

    @ESField(type= FieldType.LONG)
    private String longValue;

    @ESField(type= FieldType.KEYWORD)
    private String keywordsValue;

    @ESField(type= FieldType.DATE)
    private String dateValue;

    @ESField(type= FieldType.DOUBLE)
    private String doubleValue;

    @ESField(type= FieldType.FLOAT)
    private String floatValue;

    @ESField(type= FieldType.INTEGER)
    private String intValue;



}
