package com.chinamobile.auth.exception;

import com.chinamobile.auth.util.ResultSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResultSet globalException(HttpServletResponse response, Exception ex){
        log.info("GlobalExceptionHandler...");
        log.info("错误代码："  + response.getStatus());

        return ResultSet.failed(ex.getMessage());
    }
}
