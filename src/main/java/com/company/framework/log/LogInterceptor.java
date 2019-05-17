package com.company.framework.log;

import com.alibaba.fastjson.JSON;
import com.company.common.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;

@Slf4j
public class LogInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        log.info(JSON.toJSONString(RequestUtil.getAllHttpRequestParams()));
//        ServletInputStream inputStream = request.getInputStream();
//        String s = StreamUtils.copyToString(inputStream, Charset.defaultCharset());
        log.info(request.getContentType());
//        request.
        return true;
    }
}
