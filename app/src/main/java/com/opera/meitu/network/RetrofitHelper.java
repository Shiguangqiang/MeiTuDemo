package com.opera.meitu.network;


import android.content.Context;
import android.provider.Settings;

import com.google.gson.GsonBuilder;
import com.opera.meitu.utils.LogUtil;
import com.opera.meitu.utils.UrlUtil;
import com.opera.meitu.utils.Util;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitHelper {

    private static RetrofitHelper instance = null;
    OkHttpClient client = new OkHttpClient();
    GsonConverterFactory factory = GsonConverterFactory.create(new GsonBuilder().create());
    private Context mContext;
    private Retrofit mRetrofit = null;

    private RetrofitHelper(Context mContext) {
        this.mContext = mContext;
        init();
    }

    private void init() {
        resetApp();
    }

    private void resetApp() {
//        添加OKHttp log拦截
        client = new OkHttpClient.Builder().
                connectTimeout(10, TimeUnit.SECONDS).
                retryOnConnectionFailure(false).
                readTimeout(10, TimeUnit.SECONDS).
                writeTimeout(10, TimeUnit.SECONDS).
                // Log信息拦截器
                        addInterceptor(new LoggingInterceptor()).
                        build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(UrlUtil.getUrl())
                .client(client)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


    }

    public static RetrofitHelper getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitHelper(context);
        }
        return instance;
    }

    public RetrofitService getService() {
        return mRetrofit.create(RetrofitService.class);
    }


    public final static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            Request request = chain.request();
            long t1 = System.nanoTime();//请求发起的时间

            String method = request.method();
            if ("POST".equals(method)) {
                String convertedString = URLDecoder.decode(Util.entityToJson(request.body()), "UTF-8");
//                LogUtil.releaseLog(String.format("发送请求 %s RequestParams: %n %s",
//                        request.url(), convertedString));
            } else {
//                LogUtil.releaseLog(String.format("发送请求 %s %n %s",
//                        request.url(), request.headers()));
            }
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();//收到响应的时间
            //这里不能直接使用response.body().string()的方式输出日志
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
            //个新的response给应用层处理
            ResponseBody responseBody = response.peekBody(1024 * 1024);
//            LogUtil.longLog(String.format("接收响应: [%s] %.1fms 返回json: ",
//                    response.request().url(), (t2 - t1) / 1e6d));
//            LogUtil.longLog(responseBody.string());

//            SystemClock.sleep(5000);
            return response;
        }
    }
}
