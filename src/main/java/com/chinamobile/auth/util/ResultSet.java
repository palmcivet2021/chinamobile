package com.chinamobile.auth.util;

/**
 * 通用返回对象
 * Created by macro on 2019/4/19.
 */
public class ResultSet<T> {
    private long code;
    private String message;
    private T data;

    protected ResultSet() {
    }

    protected ResultSet(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    protected ResultSet(long code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> ResultSet<T> success(T data) {
        return new ResultSet<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> ResultSet<T> success(T data, String message) {
        return new ResultSet<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 成功返回结果
     *
     */
    public static <T> ResultSet<T> success() {
        return new ResultSet<T>(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMessage());
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> ResultSet<T> failed(ResultCode errorCode) {
        return new ResultSet<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> ResultSet<T> failed(ResultCode errorCode,String msg) {
        return new ResultSet<T>(errorCode.getCode(), msg, null);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> ResultSet<T> failed(String message) {
        return new ResultSet<T>(ResultCode.ERROR.getCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> ResultSet<T> failed() {
        return failed(ResultCode.ERROR);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> ResultSet<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> ResultSet<T> validateFailed(String message) {
        return new ResultSet<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> ResultSet<T> unauthorized(T data) {
        return new ResultSet<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> ResultSet<T> forbidden(T data) {
        return new ResultSet<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
