package com.htcs.kehaoduo.common.util.token;

/**
 * Created by htcs on 2017/7/19.
 */
public class VerifyResult<T> {
    private ResultCode resultCode;
    private T data;

    public VerifyResult(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public VerifyResult(ResultCode resultCode, T data) {
        this.resultCode = resultCode;
        this.data = data;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static enum ResultCode {
        OK(1, "验证成功！"),
        SIGNED_ERROR(2, "签名错误!"),
        EXPIRED(3, "token过期!");

        private int code;
        private String result;

        private ResultCode(int code, String result) {
            this.code = code;
            this.result = result;
        }

        @Override
        public String toString() {
            return this.result;
        }
    }
}
