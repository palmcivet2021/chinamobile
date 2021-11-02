package com.chinamobile.auth.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;

@Aspect
@Component
public class WebLogAspect {

    private final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(public * com.chinamobile.auth.controller..*.*(..))")  //切入点描述 这个是controller包的切入点
    public void controllerLog(){};  //签名，切入点的名称

    @Before("controllerLog()")
    public void logBeforeController(JoinPoint joinPoint){

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();//这个RequestContextHolder是Springmvc提供来获得请求的东西
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();

        // 记录下请求内容
        logger.info("################URL : " + request.getRequestURL().toString());
        logger.info("################HTTP_METHOD : " + request.getMethod());
        logger.info("################IP : " + request.getRemoteAddr());
        Enumeration<String> parameterNames = request.getParameterNames();
        Object[] objs = joinPoint.getArgs();
        StringBuilder sb = new StringBuilder();
        int i=1;
        while (parameterNames.hasMoreElements()){
            sb.append(parameterNames.nextElement()).append("=").append(objs[i]).append(",");
            logger.info("####paramValue="+objs[i]);
            i++;
        }

        if(sb.lastIndexOf(",") > 0) {
            logger.info("################THE Parameters OF THE CONTROLLER : " + sb.toString().substring(0, sb.lastIndexOf(",")));
            logger.info("################THE ARGS OF THE CONTROLLER : " + Arrays.toString(joinPoint.getArgs()));
        }

        //下面这个getSignature().getDeclaringTypeName()是获取包+类名的   然后后面的joinPoint.getSignature.getName()获取了方法名
        logger.info("################CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        //logger.info("################TARGET: " + joinPoint.getTarget());//返回的是需要加强的目标类的对象
        //logger.info("################THIS: " + joinPoint.getThis());//返回的是经过加强后的代理类的对象

    }

}
