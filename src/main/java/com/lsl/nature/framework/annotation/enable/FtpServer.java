package com.lsl.nature.framework.annotation.enable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FtpServer implements Server {
    @Override
    public void invoke() {
        log.info("FtpServer invoke.......");
    }
}
