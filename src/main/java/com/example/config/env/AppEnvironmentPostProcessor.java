package com.example.config.env;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.List;

/**
 * 或者自定义PropertyPlaceholderConfigurer加载配置文件
 */
@Slf4j
public class AppEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

//    private List<PropertySourceLoader> propertySourceLoaders;

    PropertySourceLoader propertySourceLoader = new PropertiesPropertySourceLoader();

    public AppEnvironmentPostProcessor() {
        super();
//        this.propertySourceLoaders = SpringFactoriesLoader.loadFactories(PropertySourceLoader.class,this.getClass().getClassLoader());
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String path = ResourceUtils.CLASSPATH_URL_PREFIX + "config/**/*.properties";
        try {
            Resource[] resources = resolver.getResources(path);
            for (Resource resource : resources) {
                List<PropertySource<?>> propertySources = loadProperty(resource);
                if (propertySources != null && !propertySources.isEmpty()) {
                    for (PropertySource<?> propertySource : propertySources) {
                        environment.getPropertySources().addLast(propertySource);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<PropertySource<?>> loadProperty(Resource resource) {
        List<PropertySource<?>> sources = null;
        if (!resource.exists()) {
            throw new IllegalArgumentException("Resource " + resource + " does not exist");
        }
        log.info("Loading properties from resource " + resource.getFilename());
        System.out.println("Loading property from resource " + resource.getFilename());
        try {
            sources = this.propertySourceLoader.load(resource.getFilename(), resource);
        } catch (IOException ex) {
            throw new IllegalStateException(
                    "Failed to load property configuration from " + resource, ex);
        }
        return sources;
    }
}
