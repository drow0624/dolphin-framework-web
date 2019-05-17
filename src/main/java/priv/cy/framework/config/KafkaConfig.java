package priv.cy.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource("classpath:config/kafka.properties")
@Data
@ConfigurationProperties(prefix = "kafka")
@Component
public class KafkaConfig {
    private String url;
    private String node;
    private Integer port;
}
