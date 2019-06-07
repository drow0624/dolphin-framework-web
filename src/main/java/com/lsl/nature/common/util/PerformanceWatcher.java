package com.lsl.nature.common.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class PerformanceWatcher {

    private Long startTime;

    private Long currentTime;

    private static PerformanceWatcher watcher = new PerformanceWatcher();

    private PerformanceWatcher(){}

    public static PerformanceWatcher getInstance(){
        return watcher;
    }

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void restart() {
        start();
    }

    public void clear(){
        startTime = null;
        currentTime = null;
    }

    public void watch(){
        if(startTime==null){
            startTime = System.currentTimeMillis();
        }
        currentTime = System.currentTimeMillis();
        log.info(String.format("执行时间[%d]毫秒",(currentTime-startTime)));
    }
}
