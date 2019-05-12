package com.example.entity;

import com.example.annotion.ESDocument;
import com.example.annotion.ESField;
import com.example.annotion.FieldType;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ESDocument(indexName = "uirb_log_index",type = "uirb_monitoring_log")
public class MonitorLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @ESField(type=FieldType.Long)
    private Long appId; //应用ID

    @ESField(type=FieldType.Keyword)
    private String appName; //应用名称

    @ESField(type=FieldType.Long)
    private String userName; //用户名

    @ESField(type=FieldType.Long)
    private Long beginTime; //开始时间

    @ESField(type=FieldType.Long)
    private Long timeElapse; //消耗时间

    @ESField(type=FieldType.Long)
    private Long dataId;   //数据ID

    @ESField(type=FieldType.Long)
    private String title;  //数据标题

    @ESField(type=FieldType.Long)
    private Long resId; //资源ID

    @ESField(type=FieldType.Long)
    private Long distResId;//重复资源ID 2019年5月8日

    @ESField(type=FieldType.Long)
    private Long catId; //分类ID

    @ESField(type=FieldType.Text)
    private String categoryIds;//分类IDS 2019年5月8日

    @ESField(type=FieldType.Text)
    private String dataSourcesUrl;//数据来源

    @ESField(type=FieldType.Long)
    private Long stdId; //标准ID

    @ESField(type=FieldType.Keyword)
    private String resName;//资源名称

    @ESField(type=FieldType.Keyword)
    private String sysName;//系统名称

    @ESField(type=FieldType.Keyword)
    private String logType;//日志模块

    @ESField(type=FieldType.Keyword)
    private String moduleName;//系统模块

    @ESField(type=FieldType.Text)
    private String uri; //请求路径

    @ESField(type=FieldType.Text)
    private String clientIp; //客户端IP

    @ESField(type=FieldType.Text)
    private String serverIp; //服务端IP

    @ESField(type=FieldType.Keyword)
    private String requestMethod; //请求方式

    @ESField(type=FieldType.Text)
    private String requestJSON; //请求参数JSON

    @ESField(type=FieldType.Text)
    private String responseJSON;//响应结果JSON

    @ESField(type=FieldType.Keyword)
    private String labels;//标签

    @ESField(type=FieldType.Keyword)
    private String categories;//分类

    @ESField(type=FieldType.Text)
    private String duplicateTitle;//重复标题

    @ESField(type=FieldType.Keyword)
    private String similarityRate;//重复率

    @ESField(type=FieldType.Keyword)
    private String superDept;//上级部门

    @ESField(type=FieldType.Keyword)
    private String subDept;//下级部门

    @ESField(type=FieldType.Text)
    private String wrongWords;//错别字

    @ESField(type=FieldType.Text)
    private String sensitiveWords;//敏感词

    @ESField(type=FieldType.Text)
    private String content;//正文
}
