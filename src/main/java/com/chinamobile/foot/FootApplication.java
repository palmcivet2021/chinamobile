package com.chinamobile.foot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableScheduling
@SpringBootApplication
@MapperScan(basePackages = {"com.chinamobile.foot"})
@EnableSwagger2
public class FootApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(FootApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
