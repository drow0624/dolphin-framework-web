package com.lsl.nature.framework.proxy;

import com.lsl.nature.framework.annotation.enable.HttpServer;
import com.lsl.nature.framework.annotation.enable.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;
import priv.cy.framework.annotation.enable.HttpServer;
import priv.cy.framework.annotation.enable.Server;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * Title: TRS 统一信息资源管理（TRS URIB）
 * Description:
 * Copyright: Copyright (c) 2004-2017 TRS信息技术有限公司
 * Company: TRS信息技术有限公司(www.trs.com.cn)
 * CreateDate:  2019/5/23
 *
 * @author yang.cao
 * @version 1.0
 */
@Slf4j
public class JdkDynamicProxy<T> implements InvocationHandler {

    private T target;

    public JdkDynamicProxy(T target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info(String.format("proxy[%s]", proxy.getClass()));
        log.info(String.format("method[%s]", method.getName()));
        log.info(String.format("args[%s]", Arrays.toString(args)));
        Object result = null;
        if(target!=null) {
            result = method.invoke(target, args);
        }
        return result;
    }

    public <T> T getProxy() {
        return (T) Proxy.newProxyInstance(ClassUtils.getDefaultClassLoader(),
                ClassUtils.getAllInterfaces(target), this);
    }

    public static void main(String[] args) {
        JdkDynamicProxy<Server> proxy = new JdkDynamicProxy<>(new HttpServer());
        Server server = proxy.getProxy();
        server.invoke();
    }
}
