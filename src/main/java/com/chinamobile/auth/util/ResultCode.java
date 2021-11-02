package com.chinamobile.auth.util;

import org.springframework.http.HttpStatus;

public enum ResultCode  {
    SUCCESS(HttpStatus.OK.value(), "操作成功"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "请求失败"),
    FORBIDDEN(HttpStatus.FORBIDDEN.value(), "请求受限"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED.value(), "接口不被允许"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "暂未登录或token已经过期！"),
    ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统错误"),
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "NOT_FOUND"),
    OVER_LIMIT(503, "接口被限制"),
    VALIDATE_FAILED(504, "参数校验错误"),
    REQUEST_TIMEOUT(HttpStatus.REQUEST_TIMEOUT.value(), "请求超时");
    private long code;
    private String message;

    private ResultCode(long code){
        this.code = code;
    }

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
