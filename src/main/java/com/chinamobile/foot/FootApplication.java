package com.chinamobile.foot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
//import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@EnableScheduling
@SpringBootApplication
@MapperScan(basePackages = {"com.chinamobile.foot"})
//@EnableHystrix
//@EnableCircuitBreaker
public class FootApplication {

    public static void main(String[] args) {
        SpringApplication.run(FootApplication.class, args);
    }

}
