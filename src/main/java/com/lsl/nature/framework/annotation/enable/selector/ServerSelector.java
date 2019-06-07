package com.lsl.nature.framework.annotation.enable.selector;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import priv.cy.framework.annotation.condition.DBTypeCondition;
import priv.cy.framework.annotation.enable.EnableServer;

import java.util.Map;


public class ServerSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata metaData) {
        Map<String, Object> enableCacheAttributes = metaData.getAnnotationAttributes(EnableServer.class.getName());
        Class type = (Class)enableCacheAttributes.get("type");
        return new String[]{type.getName()};
    }
}
