package com.opera.meitu.utils;

import android.content.Context;


/**
 * Created by Sgq
 * Create Date 2019/4/18 and 11:47
 * desc:DensityUtil
 */

public class DensityUtil {
    /**
     *  
     *      * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     *      
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     *  
     *      * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     *      
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param sp
     * @return
     */
    public static float sp2px(Context context, float sp) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * scaledDensity;
    }


    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}
