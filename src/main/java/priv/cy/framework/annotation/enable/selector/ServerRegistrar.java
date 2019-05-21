package priv.cy.framework.annotation.enable.selector;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import priv.cy.framework.annotation.enable.EnableServer;

import java.util.Map;

public class ServerRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        Map<String, Object> enableCacheAttributes = metadata.getAnnotationAttributes(EnableServer.class.getName());
        Class type = (Class) enableCacheAttributes.get("type");
        BeanDefinitionBuilder bdb = BeanDefinitionBuilder.rootBeanDefinition(type);
        BeanDefinition beanDefinition = bdb.getBeanDefinition();
        registry.registerBeanDefinition(type.getName(), beanDefinition);
    }
}
