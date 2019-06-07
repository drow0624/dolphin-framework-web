package com.lsl.nature.common.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil implements ApplicationContextAware, BeanFactory {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return SpringContextUtil.applicationContext.getBean(name);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return SpringContextUtil.applicationContext.getBean(name,requiredType);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return SpringContextUtil.applicationContext.getBean(name,args);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return SpringContextUtil.applicationContext.getBean(requiredType);
    }

    @Override
    public <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
        return SpringContextUtil.applicationContext.getBean(requiredType,args);
    }

    @Override
    public <T> ObjectProvider<T> getBeanProvider(Class<T> requiredType) {
        return SpringContextUtil.applicationContext.getBeanProvider(requiredType);
    }

    @Override
    public <T> ObjectProvider<T> getBeanProvider(ResolvableType requiredType) {
        return SpringContextUtil.applicationContext.getBeanProvider(requiredType);
    }

    @Override
    public boolean containsBean(String name) {
        return SpringContextUtil.applicationContext.containsBean(name);
    }

    @Override
    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return SpringContextUtil.applicationContext.isSingleton(name);
    }

    @Override
    public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        return SpringContextUtil.applicationContext.isPrototype(name);
    }

    @Override
    public boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException {
        return SpringContextUtil.applicationContext.isTypeMatch(name,typeToMatch);
    }

    @Override
    public boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException {
        return SpringContextUtil.applicationContext.isTypeMatch(name,typeToMatch);
    }

    @Override
    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return SpringContextUtil.applicationContext.getType(name);
    }

    @Override
    public String[] getAliases(String name) {
        return SpringContextUtil.applicationContext.getAliases(name);
    }
}
