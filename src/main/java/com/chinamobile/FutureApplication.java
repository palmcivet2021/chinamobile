package com.chinamobile;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@MapperScan(basePackages = {"com.chinamobile"})
@EnableSwagger2
@EnableScheduling
public class FutureApplication {
    public static void main(String[] args) {
        SpringApplication.run(FutureApplication.class, args);
    }
}
