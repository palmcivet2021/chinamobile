package com.chinamobile.foot;

import com.chinamobile.foot.util.SpringContextUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
//import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@MapperScan(basePackages = {"com.chinamobile.foot.dao"})
//@EnableHystrix
//@EnableCircuitBreaker
public class FootApplication {

    public static void main(String[] args) {
        SpringApplication.run(FootApplication.class, args);
    }

}
