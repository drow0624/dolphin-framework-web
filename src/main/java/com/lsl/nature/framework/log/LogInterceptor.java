package com.lsl.nature.framework.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
