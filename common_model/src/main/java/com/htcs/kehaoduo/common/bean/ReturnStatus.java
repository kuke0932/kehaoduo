package com.htcs.kehaoduo.common.bean;

/**
 * @param <T>
 * @author zhaokaiyuan
 * @date 17-7-11
 */
public class ReturnStatus<T> {
    private Integer status;
    private String message;
    private T data;

    public ReturnStatus() {
        super();
        this.status = StatusCode.OK.getCode();
    }

    public ReturnStatus(Integer status) {
        super();
        this.status = status;
    }

    public ReturnStatus(T data) {
        super();
        this.status = StatusCode.OK.getCode();
        this.message = "请求成功！";
        this.data = data;
    }

    public ReturnStatus(String message) {
        super();
        this.status = StatusCode.OK.getCode();
        this.message = message;
    }

    public ReturnStatus(StatusCode status) {
        super();
        this.status = status.getCode();
    }

    public ReturnStatus(Integer status, String msg) {
        super();
        this.status = status;
        this.message = msg;
    }

    public ReturnStatus(Integer status, String msg, T data) {
        super();
        this.status = status;
        this.message = msg;
        this.data = data;
    }

    public ReturnStatus(StatusCode status, String msg) {
        super();
        this.status = status.getCode();
        this.message = msg;
    }

    public ReturnStatus(StatusCode status, String msg, T data) {
        super();
        this.status = status.getCode();
        this.message = msg;
        this.data = data;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setStatus(StatusCode status) {
        this.status = status.getCode();
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

    public enum StatusCode {
        OK(200, "Ok"),
        FOUND(302, "Found"),
        NOT_MODIFIED(304, "Not modified"),
        BAD_REQUEST(400, "Bad reuqest"),
        UNAUTHORIZED(401, "Unauthorized"),
        PAYMENT_REQUIRED(402, "Payment Required"),
        FORBIDDEN(403, "Forbidden"),
        NOT_FOUNT(404, " Not found"),
        METHOD_NOT_ALLOWED(405, "Method Not Allowed"),

        LOGIN_EXPIRED(490, "Login expired"),


        INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
        NOT_IMPLEMENTED(501, "Not Implemented"),
        BAD_GATEWAY(502, "Bad Gateway"),
        SERVICE_UNAVAILABLE(503, "Service Unavailable"),

        SQL_ERROR(600, "Sql Error");


        private int code;
        private String msg;

        private StatusCode(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int value() {
            return code;
        }

        public int getCode() {
            return code;
        }

        @Override
        public String toString() {
            return this.msg;
        }
    }

}
