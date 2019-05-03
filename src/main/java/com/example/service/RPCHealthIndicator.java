package com.example.service;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component("RPC001")
public class RPCHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        int errorCode = check();
        //定义服务操作检测信息
        if (errorCode != 0) {
            return Health.up()
                    .withDetail("status", errorCode)
                    .withDetail("message", "服务故障")
                    .build();
        }
        return Health.up().build();
    }

    private int check() {
        // 对监控对象的检测操作
        return HttpStatus.NOT_FOUND.value();
    }
}
