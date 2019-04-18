package com.opera.meitu.utils;

/**
 * Created by Sgq
 * Create Date 2019/4/18 and 11:47
 * desc:  UrlUtil
 */

public class UrlUtil {
    //三个版本
    public final static int VERSION_DEBUG = 0;
    public final static int VERSION_PRE_RELEASE = 1;
    public final static int VERSION_RELEASE = 2;
    public final static int VERSION_DEV = 3;
    //
//    //    预先发布
    private static final String DEV_URL = "https://www.baidu.com/";


    public static String getUrl() {
        return DEV_URL;

    }
}
