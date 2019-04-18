package com.opera.meitu.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Sgq
 * Create Date 2019/4/18 and 11:47
 * desc:ToastUtil
 */
public class ToastUtil {
    private Toast mToast;

    public void showToast(Context context, String text) {
        if (null == mToast) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        try {
            mToast.show();
        } catch (Exception e) {
        }
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }
}
