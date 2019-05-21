package priv.cy.framework.annotation.enable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Configuration
public class HttpServer implements Server{
    @Override
    public void invoke() {
        log.info("HttpServer invoke.......");
    }
}
