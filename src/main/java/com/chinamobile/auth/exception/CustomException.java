package com.chinamobile.auth.exception;

import lombok.Data;

@Data
public class CustomException extends Exception{
    private long code;
    private String msg;

    public CustomException(){}

    public CustomException(long code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public CustomException(String msg){
        this.msg = msg;
    }

}
