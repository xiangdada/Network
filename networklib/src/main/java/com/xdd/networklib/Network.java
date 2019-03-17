package com.xdd.networklib;

import android.content.Context;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xiangpengfei on 2018/12/19.
 */
public class Network {
    private static final int DEFAULT_CONNECT_TIME_OUT = 10;
    private static final int DEFAULT_READ_TIME_OUT = 30;
    private Retrofit retrofit;
    private String token = "";
    private Context context;

    private Network() {

    }

    public void init(Context context, String baseUrl, OkHttpClient client) {
        this.context = context;
        retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    public void init(Context context, String baseUrl) {
        init(context, baseUrl, defaulOkHttpClient(context));
    }

    private OkHttpClient defaulOkHttpClient(final Context context) {
        File cacheFile = new File(context.getExternalCacheDir(), context.getPackageName());
        final Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetUtil.isNetworkConnected(context)) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (!NetUtil.isNetworkConnected(context)) {
                    int maxAge = 0;
                    // 有网络时 设置缓存超时时间0个小时
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader(context.getPackageName())// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader(context.getPackageName())
                            .build();
                }
                return response;
            }
        };

        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder requsetBuilder = request.newBuilder()
                        .addHeader("Accept-Encoding", "gzip")
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json; charset=utf-8")
                        .method(request.method(), request.body());
                requsetBuilder.addHeader("token", token);

                Request request1 = requsetBuilder.build();
                return chain.proceed(request1);
            }
        };

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if (BuildConfig.DEBUG) {
                    // 区分这里的两个Logger不是同一个开源库中的类
                    Logger.d(message);
                }
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_CONNECT_TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true); // 设置错误重连为true
        builder.cache(cache).addInterceptor(cacheInterceptor);
        builder.addInterceptor(headerInterceptor);
        builder.addInterceptor(loggingInterceptor);
        return builder.build();
    }


    public <T> T customRequest(String baseUrl, Class<T> service, OkHttpClient client) {
        Retrofit retrofit1 = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
        return retrofit1.create(service);
    }

    public <T> T customRequest(Context context, String baseUrl, Class<T> service) {
        Retrofit retrofit1 = new Retrofit.Builder()
                .client(defaulOkHttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
        return retrofit1.create(service);
    }

    public static Network getInstance() {
        return NetworkFactory.instanc;
    }

    private static class NetworkFactory {
        private static final Network instanc = new Network();
    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }
}
