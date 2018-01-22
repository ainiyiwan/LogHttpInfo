package com.xiao.loghttpinfo.http;

import com.xiao.loghttpinfo.HttpLogger;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求工具
 */
public class RetrofitManager {

    private static RetrofitManager sInstance = new RetrofitManager();
    private OkHttpClient mOkHttpClient;

    public static RetrofitManager getInstance() {
        return sInstance;
    }

    private RetrofitManager() {
    }

    /**
     * 获取ApiService
     *
     * @return 所有apiService
     */
    public ApiService getApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.baseUrl)
                .client(okhttpclient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiService.class);
    }

    /**
     * 初始化okhttpclient.
     *
     * @return okhttpClient
     */
    private OkHttpClient okhttpclient() {
        if (mOkHttpClient == null) {
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .addNetworkInterceptor(logInterceptor)
                    .build();
        }
        return mOkHttpClient;
    }

}
