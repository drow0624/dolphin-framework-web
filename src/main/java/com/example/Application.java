package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

//spring-boot-autoconfigure-2.1.3.RELEASE.jar
//WebMvcAutoConfiguration
//PathMatchingResourcePatternResolver
@SpringBootApplication
public class Application {

    @Bean
    @Profile("dev")
    public Runnable createRunnable(Environment env) {
        return () -> {
            System.out.println("spring boot is kafka running ip " + env.getProperty("kafka.url"));
        };
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        context.getBean(Runnable.class).run();
        System.out.println(context.getEnvironment().getProperty("kafka.url"));
    }

}
