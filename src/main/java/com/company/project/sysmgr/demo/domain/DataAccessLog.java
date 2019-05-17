package com.company.project.sysmgr.demo.domain;

import com.company.common.es.annotation.ESDocument;
import com.company.common.es.annotation.FieldType;
import com.company.common.es.annotation.ESField;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * Created by WangHaoyu on 2018/11/23.
 * 接口日志 实时数据接入 历史数据接入
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@ESDocument(indexName = "uirb_log_index",type = "uirb_data_access")
public class DataAccessLog implements Serializable {


//    elasticsearch.type=uirb_system_log
//    elasticsearch.apitype=uirb_data_access


    private static final long serialVersionUID = 1L;

    private String id;

    @ESField(type= FieldType.LONG)
    private Long appId; //应用ID

    @ESField(type= FieldType.KEYWORD)
    private String appName; //应用名称

    @ESField(type= FieldType.KEYWORD)
    private String userName; //用户名

    @ESField(type= FieldType.LONG)
    private Long beginTime; //开始时间

    @ESField(type= FieldType.LONG)
    private Long timeElapse; //消耗时间

    @ESField(type= FieldType.LONG)
    private Long dataId;   //数据ID

    @ESField(type= FieldType.TEXT)
    private String title;  //数据标题

    @ESField(type= FieldType.LONG)
    private Long resId; //资源ID

    @ESField(type= FieldType.LONG)
    private Long catId; //分类ID

    @ESField(type= FieldType.LONG)
    private Long stdId; //标准ID

    @ESField(type= FieldType.KEYWORD)
    private String resName;//资源名称

    @ESField(type= FieldType.KEYWORD)
    private String sysName;//系统名称

    @ESField(type= FieldType.KEYWORD)
    private String logType;//日志类型

    @ESField(type= FieldType.KEYWORD)
    private String moduleName;//系统模块

    @ESField(type= FieldType.TEXT)
    private String uri; //请求路径

    @ESField(type= FieldType.TEXT)
    private String clientIp; //客户端IP

    @ESField(type= FieldType.TEXT)
    private String serverIp; //服务端IP

    @ESField(type= FieldType.KEYWORD)
    private String requestMethod; //请求方式

    @ESField(type= FieldType.TEXT)
    private String requestJSON; //请求参数JSON

    @ESField(type= FieldType.TEXT)
    private String responseJSON;//响应结果JSON

    @ESField(type= FieldType.KEYWORD)
    private String status;//成功/失败

    @ESField(type= FieldType.TEXT)
    private String desc;//描述

    @ESField(type= FieldType.TEXT)
    private String categoryIds;//分类IDS
}
