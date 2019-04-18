package com.opera.meitu.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import java.util.List;


/**
 * by Soda on 2018/3/23.
 */

public class Util {


    public static Boolean isEmptyList(List list) {
        return list == null || list.size() == 0;
    }

    public static String listToString(List<String> list) {

        if (list == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        boolean first = true;

        //第一个前面不拼接","
        for (String string : list) {
            if (first) {
                first = false;
            } else {
                result.append(";");
            }
            result.append(string);
        }
        return result.toString();

    }



    public static String getMoneyString(int money) {
        if (money == 0) {
            return "¥0";
        } else if (money > 0) {
            return "¥" + Math.abs(money);
        } else {
            return "-¥" + Math.abs(money);
        }
    }

    public static Object jsonToEntity(String json, Class<?> cls) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, cls);
        } catch (Exception e) {
            LogUtil.e("jsonToEntity exception e= " , e.toString());
            return null;
        }
    }

    public static String entityToJson(Object object) {
        Gson gson = new Gson();
        try {
            String retGson = gson.toJson(object);
            LogUtil.e("entityToJson, " , gson.toJson(object));
            return retGson;
        } catch (Exception e) {
            LogUtil.e("entityToJson, exception e= " , e.toString());
            return "";
        }

    }


    public static String getProcessName(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = activityManager.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        int pid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }


}
