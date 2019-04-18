package com.opera.meitu.base;

/**
 *
 */
public class BaseResult extends BaseBean {

    private static final long serialVersionUID = 132534645567L;

    private int code = 0;
    private String msg;
    private int errCode = -1;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    @Override
    public String toString() {
        return "BaseResult [code=" + code + ", msg=" + msg + ", errCode=" + errCode + "]";
    }


}
