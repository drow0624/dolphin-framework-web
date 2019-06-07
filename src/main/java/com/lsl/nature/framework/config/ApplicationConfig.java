package com.lsl.nature.framework.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

//ConfigurationPropertiesBindingPostProcessor
//ConfigurationPropertiesAutoConfiguration
//@Component或者@EnableConfigurationProperties({ApplicationConfig.class})
//@Component
//@ConfigurationProperties(prefix = "app")
@Data
@ToString
public class ApplicationConfig {
    private String name;
    private String uri;
    private String uuid;
    private Integer serial;
    private Integer port;

}
