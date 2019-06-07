package com.lsl.nature.project.sysmgr.demo.domain;

import com.lsl.nature.common.es.annotation.ESDocument;
import com.lsl.nature.common.es.annotation.ESField;
import com.lsl.nature.common.es.annotation.FieldType;
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

    @ESField(type= FieldType.LONG)
    private Long appId; //应用ID

    @ESField(type=FieldType.KEYWORD)
    private String appName; //应用名称

    @ESField(type=FieldType.LONG)
    private String userName; //用户名

    @ESField(type=FieldType.LONG)
    private Long beginTime; //开始时间

    @ESField(type=FieldType.LONG)
    private Long timeElapse; //消耗时间

    @ESField(type=FieldType.LONG)
    private Long dataId;   //数据ID

    @ESField(type=FieldType.LONG)
    private String title;  //数据标题

    @ESField(type=FieldType.LONG)
    private Long resId; //资源ID

    @ESField(type=FieldType.LONG)
    private Long distResId;//重复资源ID 2019年5月8日

    @ESField(type=FieldType.LONG)
    private Long catId; //分类ID

    @ESField(type=FieldType.TEXT)
    private String categoryIds;//分类IDS 2019年5月8日

    @ESField(type=FieldType.TEXT)
    private String dataSourcesUrl;//数据来源

    @ESField(type=FieldType.LONG)
    private Long stdId; //标准ID

    @ESField(type=FieldType.KEYWORD)
    private String resName;//资源名称

    @ESField(type=FieldType.KEYWORD)
    private String sysName;//系统名称

    @ESField(type=FieldType.KEYWORD)
    private String logType;//日志模块

    @ESField(type=FieldType.KEYWORD)
    private String moduleName;//系统模块

    @ESField(type=FieldType.TEXT)
    private String uri; //请求路径

    @ESField(type=FieldType.TEXT)
    private String clientIp; //客户端IP

    @ESField(type=FieldType.TEXT)
    private String serverIp; //服务端IP

    @ESField(type=FieldType.KEYWORD)
    private String requestMethod; //请求方式

    @ESField(type=FieldType.TEXT)
    private String requestJSON; //请求参数JSON

    @ESField(type=FieldType.TEXT)
    private String responseJSON;//响应结果JSON

    @ESField(type=FieldType.KEYWORD)
    private String labels;//标签

    @ESField(type=FieldType.KEYWORD)
    private String categories;//分类

    @ESField(type=FieldType.TEXT)
    private String duplicateTitle;//重复标题

    @ESField(type=FieldType.KEYWORD)
    private String similarityRate;//重复率

    @ESField(type=FieldType.KEYWORD)
    private String superDept;//上级部门

    @ESField(type=FieldType.KEYWORD)
    private String subDept;//下级部门

    @ESField(type=FieldType.TEXT)
    private String wrongWords;//错别字

    @ESField(type=FieldType.TEXT)
    private String sensitiveWords;//敏感词

    @ESField(type=FieldType.TEXT)
    private String content;//正文
}
