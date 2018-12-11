package com.htcs.kehaoduo.common.exception;

/**
 * Created by htcs on 2017/8/24.
 */
public class PageErrorException extends RuntimeException {

    public PageErrorException() {
        super();
    }

    public PageErrorException(String message) {
        super(message);
    }

    public PageErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}