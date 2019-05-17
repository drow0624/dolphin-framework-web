package priv.cy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

//spring-boot-autoconfigure-2.1.3.RELEASE.jar
//WebMvcAutoConfiguration
//PathMatchingResourcePatternResolver
@SpringBootApplication
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
    }

}
