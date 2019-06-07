package com.lsl.nature.framework.annotation.enable;


import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
//@Import(ServerSelector.class)
@Import(HttpServer.class)//+@Configuration
//@Import(ServerRegistrar.class)
public @interface EnableServer {
    ServerType value() default ServerType.HTTP;
    Class<?> type() default HttpServer.class;
}
