package com.example.entity;


import com.example.annotion.ESDocument;
import com.example.annotion.ESField;
import com.example.annotion.FieldType;
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

    @ESField(type= FieldType.Text)
    private String logUser;

    @ESField(type= FieldType.Text)
    private String trueName;

    @ESField(type= FieldType.Text)
    private String operateType;

    @ESField(type= FieldType.Text)
    private String logTime;

    @ESField(type= FieldType.Text)
    private String logUserIP;

    @ESField(type= FieldType.Text)
    private String objName;

    @ESField(type= FieldType.Text)
    private String logDesc;

    @ESField(type= FieldType.Text)
    private String timeUsed;

    @ESField(type= FieldType.Text)
    private String taskTitle;

    @ESField(type= FieldType.Text)
    private String taskStatus;

    @ESField(type= FieldType.Text)
    private String logUserGroup;

    @ESField(type= FieldType.Text)
    private String logResult;

    @ESField(type= FieldType.Text)
    private String errorType;

    @ESField(type= FieldType.Text)
    private String warnType;

    @ESField(type= FieldType.Text)
    private String debugType;

    @ESField(type= FieldType.Text)
    private String infoType;

    @ESField(type= FieldType.Text)
    private String logLevel;

    @ESField(type= FieldType.Text)
    private String classInfo;

    @ESField(type= FieldType.Text)
    private String module;

    @ESField(type= FieldType.Text)
    private String machineIP;

    @ESField(type= FieldType.Text)
    private String siteId;

    @ESField(type= FieldType.Keyword)
    private String moduleName;

    @ESField(type= FieldType.Text)
    private String type;


}
