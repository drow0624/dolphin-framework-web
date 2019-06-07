package com.lsl.nature;

import com.lsl.nature.framework.annotation.enable.EnableServer;
import com.lsl.nature.framework.annotation.enable.FtpServer;
import com.lsl.nature.framework.annotation.enable.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import priv.cy.framework.annotation.enable.EnableServer;
import priv.cy.framework.annotation.enable.FtpServer;
import priv.cy.framework.annotation.enable.Server;

//spring-boot-autoconfigure-2.1.3.RELEASE.jar
//WebMvcAutoConfiguration
//PathMatchingResourcePatternResolver
@SpringBootApplication
@EnableServer(type= FtpServer.class)
public class Application {

//    @Bean
//    @Profile("dev")
//    public Runnable createRunnable(Environment env) {
//        return () -> {
//            System.out.println("hxfm is kafka running ip :" + env.getProperty("kafka.url"));
//        };
//    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
//        context.getBean(Runnable.class).run();
//        System.out.println(context.getEnvironment().getProperty("kafka.url"));
        Server server = context.getBean(Server.class);
        server.invoke();
        //实现
    }

}
