package com.example.entity;

import com.alibaba.fastjson.JSON;
import com.example.annotion.ESDocument;
import com.example.annotion.ESField;
import com.example.annotion.FieldType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Map;

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

    @ESField(type= FieldType.Long)
    private Long appId; //应用ID

    @ESField(type= FieldType.Keyword)
    private String appName; //应用名称

    @ESField(type= FieldType.Keyword)
    private String userName; //用户名

    @ESField(type= FieldType.Long)
    private Long beginTime; //开始时间

    @ESField(type= FieldType.Long)
    private Long timeElapse; //消耗时间

    @ESField(type= FieldType.Long)
    private Long dataId;   //数据ID

    @ESField(type= FieldType.Text)
    private String title;  //数据标题

    @ESField(type= FieldType.Long)
    private Long resId; //资源ID

    @ESField(type= FieldType.Long)
    private Long catId; //分类ID

    @ESField(type= FieldType.Long)
    private Long stdId; //标准ID

    @ESField(type= FieldType.Keyword)
    private String resName;//资源名称

    @ESField(type= FieldType.Keyword)
    private String sysName;//系统名称

    @ESField(type= FieldType.Keyword)
    private String logType;//日志类型

    @ESField(type= FieldType.Keyword)
    private String moduleName;//系统模块

    @ESField(type= FieldType.Text)
    private String uri; //请求路径

    @ESField(type= FieldType.Text)
    private String clientIp; //客户端IP

    @ESField(type= FieldType.Text)
    private String serverIp; //服务端IP

    @ESField(type= FieldType.Keyword)
    private String requestMethod; //请求方式

    @ESField(type= FieldType.Text)
    private String requestJSON; //请求参数JSON

    @ESField(type= FieldType.Text)
    private String responseJSON;//响应结果JSON

    @ESField(type= FieldType.Keyword)
    private String status;//成功/失败

    @ESField(type= FieldType.Text)
    private String desc;//描述

    @ESField(type= FieldType.Text)
    private String categoryIds;//分类IDS
}
