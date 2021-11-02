package com.chinamobile.auth.util;

public class Result<T> {
    private long code;
    private String msg;
    private T data;

    public Result(){
    }

    public Result(long code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public Result(long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T>Result<T> SUCCESS(){
        return new Result(200, "success");
    }

    public static <T>Result<T> SUCCESS(T data){
        return new Result(200, "success", data);
    }
}
