package com.chinamobile;

import com.chinamobile.config.PropertiesListener;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableScheduling
@SpringBootApplication
@MapperScan(basePackages = {"com.chinamobile.foot"})
@EnableSwagger2
public class FutureApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(FutureApplication.class);
        application.addListeners(new PropertiesListener("mat-config.properties"));
        application.run(args);
    }
}
