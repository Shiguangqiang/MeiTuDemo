package com.opera.meitu.network;


public class ApiException extends RuntimeException {

    public static final int NO_NET_EXCEPTION = 10000; //无网络
    public static final int SERVER_EXCEPTION = 10002; //404, > 500 HTTPEXCEPTION
    public static final int TOKEN_EXPIRED_EXCEPTION = 10003; //TOKEN过期
    public static final int API_ERROR_EXCEPTION = 10004;//API返回code != 0
    public static final int OTHER_EXCEPTION = 10005; // 所有其他Exceptjion

    public static final String SERVER_EXCEPTION_STRING = "服务器出小差啦，程序员哥哥正在努力";
    public static final String TOKEN_EXPIRED_EXCEPTION_STRING = "登录过期，请重新登录"; //TOKEN过期
    public static final String OTHER_EXCEPTION_STRING = "有点小问题，请稍后再试"; // 所有其他Exception
    public static final String NO_HTTP_EXCEPTION = "网络不给力哦"; // 无网络


    private int code;

    public ApiException(String msg, int code) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
