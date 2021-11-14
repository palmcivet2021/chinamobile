package com.chinamobile;

import com.chinamobile.config.PropertiesListener;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@MapperScan(basePackages = {"com.chinamobile.foot"})
public class FutureApplication {
    public static void main(String[] args){
        //SpringApplication.run(FutureApplication.class, args);

        SpringApplication application = new SpringApplication(FutureApplication.class);
        application.addListeners(new PropertiesListener("mat-config.properties"));
        application.run(args);
    }
}
