package com.lsl.nature.framework.annotation.enable.selector;

import com.lsl.nature.framework.annotation.enable.EnableServer;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;


public class ServerSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata metaData) {
        Map<String, Object> enableCacheAttributes = metaData.getAnnotationAttributes(EnableServer.class.getName());
        Class type = (Class)enableCacheAttributes.get("type");
        return new String[]{type.getName()};
    }
}
