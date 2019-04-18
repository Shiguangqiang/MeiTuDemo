package com.opera.meitu.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


/**
 * Created by Sgq
 * Create Date 2019/4/18 and 11:47
 * desc:NetUtil
 */

class NetUtil {


    companion object {
        fun isNetConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (cm != null) {

                //todo 权限
                val activeNetworkInfo = cm?.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo?.isAvailable) {
                    return true
                }
            }
            return false
        }

        //临时放置此处，因为三个HTTP请求框架都要调用
        fun handleTokenError(context: Context) {

//            UserUtil(context).logout()

        }


//        fun isNetworkAvailable(context: Context): Boolean {
//            val connectivity = context
//                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//            if (connectivity == null) {
//
//                return false
//            } else {
//                val info = connectivity.allNetworkInfo
//                if (info != null) {
//                    for (i in info.indices) {
//                        if (info[i].state == NetworkInfo.State.CONNECTED) {
//
//                            return true
//                        }
//                    }
//                }
//            }
//            return false
//        }


    }
}
