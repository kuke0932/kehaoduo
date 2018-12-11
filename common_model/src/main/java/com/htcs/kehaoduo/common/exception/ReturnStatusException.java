package com.htcs.kehaoduo.common.exception;

import com.htcs.kehaoduo.common.bean.ReturnStatus;

/**
 * Created by htcs on 2017/7/25.
 */
public class ReturnStatusException extends RuntimeException {

    private ReturnStatus.StatusCode returnStatusCode;

    public ReturnStatusException() {
        super();
    }

    public ReturnStatusException(ReturnStatus.StatusCode code, String message) {
        super(message);
        this.returnStatusCode = code;
    }

    public ReturnStatusException(ReturnStatus.StatusCode code, String message, Throwable throwable) {
        super(message, throwable);
        this.returnStatusCode = code;
    }


    public ReturnStatus.StatusCode getReturnStatusCode() {
        return returnStatusCode;
    }

    public void setReturnStatusCode(ReturnStatus.StatusCode returnStatusCode) {
        this.returnStatusCode = returnStatusCode;
    }
}
