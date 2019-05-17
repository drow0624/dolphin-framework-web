package priv.cy.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Title: TRS 统一信息资源管理（TRS URIB）
 * Description:
 * Copyright: Copyright (c) 2004-2017 TRS信息技术有限公司
 * Company: TRS信息技术有限公司(www.trs.com.cn)
 * CreateDate:  2018/12/5
 * 获取HttpRequest参数
 * HTTP Request 工具
 *
 * @author yang.cao
 * @version 1.0
 */
@Slf4j
public abstract class RequestUtil {

    public static String getStringAttribute(ServletRequest request, String attributeName) {
        String value = null;
        if (request != null) {
            Object attribute = request.getAttribute(attributeName);
            if (attribute != null) {
                value = attribute.toString();
            }
        }
        return value;
    }

    public static String getRequestParam(String paramName){
        return getAllHttpRequestParams().get(paramName);
    }
    public static Long getLongAttribute(ServletRequest request, String attributeName) {
        Long value = null;
        if (request != null) {
            Object attribute = request.getAttribute(attributeName);
            if (attribute != null) {
                value = Long.valueOf(attribute.toString());
            }
        }
        return value;
    }

    public static Boolean getBooleanAttribute(ServletRequest request, String attributeName) {
        Boolean value = false;
        if (request != null) {
            Object attribute = request.getAttribute(attributeName);
            if (attribute != null) {
                value = Boolean.valueOf(attribute.toString());
            }
        }
        return value;
    }

    public static Integer getIntegerAttribute(ServletRequest request, String attributeName) {
        Integer value = null;
        if (request != null) {
            Object attribute = request.getAttribute(attributeName);
            if (attribute != null) {
                value = Integer.valueOf(attribute.toString());
            }
        }
        return value;
    }

    public static Map<String, String> getUriTemplateVariables(HttpServletRequest request) {
        if (request != null) {
            NativeWebRequest webRequest = new ServletWebRequest(request);
            return (Map<String, String>) webRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE,
                    RequestAttributes.SCOPE_REQUEST);
        }
        return new HashMap<>();
    }

    /**
     * 获取请求参数，不包含POST方式RAW和路径参数
     *
     * @param request
     * @return
     */
    public static Map<String, String> getHttpRequestParams(HttpServletRequest request) {
        Map<String, String> requestParams = new HashMap<>();
        if (request != null) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            if (parameterMap != null && !parameterMap.isEmpty()) {
                parameterMap.forEach((key, values) -> {
                    if (values != null && values.length > 0) {
                        requestParams.put(key, values[0]);
                    }
                });
            }
        }
        return requestParams;
    }


    public static Map<String, String> getAllHttpRequestParams(HttpServletRequest request) {
        Map<String, String> httpRequestParams = getHttpRequestParams(request);
        Map<String, String> uriTemplateVariables = getUriTemplateVariables(request);
        if (uriTemplateVariables != null && !uriTemplateVariables.isEmpty()) {
            httpRequestParams.putAll(uriTemplateVariables);
        }
        return httpRequestParams;
    }

    public static Map<String, String> getAllHttpRequestParams() {
        HttpServletRequest request = getHttpServletRequest();
        Map<String, String> httpRequestParams = new HashMap<>();
        if (request != null) {
            httpRequestParams = getHttpRequestParams(request);
            Map<String, String> uriTemplateVariables = getUriTemplateVariables(request);
            if (uriTemplateVariables != null && !uriTemplateVariables.isEmpty()) {
                httpRequestParams.putAll(uriTemplateVariables);
            }
        }
        return httpRequestParams;
    }

    public static HttpServletRequest getHttpServletRequest() {
        if (RequestContextHolder.getRequestAttributes() == null) {
            return null;
        }
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getHttpServletResponse() {
        return ((ServletWebRequest) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static String getClientIp() {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        String clientIp = "";
        if (httpServletRequest != null) {
            clientIp = httpServletRequest.getRemoteHost();
        }
        return clientIp;
    }

    public static String getServerIp() {
        String machineIP = "";
        try {
            machineIP = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception var5) {
            log.error("获取机器IP失败！", var5);
        }
        return machineIP;
    }

    public static String getURI() {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        String uri = "";
        if (httpServletRequest != null) {
            uri = httpServletRequest.getRequestURI();
        }
        return uri;
    }

    public static String getRequestMethod() {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        String method = "";
        if (httpServletRequest != null) {
            method = httpServletRequest.getMethod();
        }
        return method;
    }
}
