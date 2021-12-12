package com.chinamobile;

import com.chinamobile.digitaltwin.websocket.WSServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@MapperScan(basePackages = {"com.chinamobile"})
@EnableSwagger2
@EnableWebSocket
//@EnableScheduling
public class FutureApplication {
    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(FutureApplication.class, args);
        //String[] beanDefinitionNames = context.getBeanDefinitionNames();
        /*for(String s : beanDefinitionNames){
            if(s.equals("webSocketClient")){
                System.out.println("在容器中找到webSocketClient");
                break;
            }

            System.out.println(s);
        }*/

        try {
            Thread.currentThread().sleep(30*1000);
            WSServer wss = (WSServer)context.getBean("webSocketServer");
        }catch (Exception e){

        }

    }

}
