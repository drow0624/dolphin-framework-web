package com.example.controller;

import com.example.config.ApplicationConfig;
import com.example.config.KafkaConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@EnableConfigurationProperties({ApplicationConfig.class})
public class HelloWorldController {

    @GetMapping("/hello/world")
    public String helloWorld() {
        return "hello world";
    }

    @Resource
    private ApplicationConfig applicationConfig;

    @GetMapping("/config/app")
    public String appConfig() {
        return applicationConfig.toString();
    }

    @Resource
    private KafkaConfig kafkaConfig;

    @GetMapping("/config/kafka")
    public String kafkaConfig() {
        return kafkaConfig.toString();
    }

    @GetMapping("/config/env")
    public String env(@Value("${env.profiles.active}") String env) {
        return env;
    }
    @GetMapping("/redis/url")
    public String redisHost(@Value("${redis.url}") String url){
        return url;
    }
}
