package plugin.test.yzk.zw.com.http.config;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

import plugin.test.yzk.zw.com.http.interceptor.ErrorInterceptor;
import plugin.test.yzk.zw.com.http.interceptor.LoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DefaultConfig implements HttpConfig {

    private Context context;
    private String baseUrl;
    private boolean debug;

    public DefaultConfig(Context context, String baseUrl) {
        this.context = context;
        this.baseUrl = baseUrl;
    }

    public DefaultConfig setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }


    @Override
    public OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .cache(new Cache(context.getCacheDir(), 20 * 1024 * 1024))
                .addInterceptor(new LoggingInterceptor().setLevel(debug ? LoggingInterceptor.Level.BODY : LoggingInterceptor.Level.NONE))
                .addInterceptor(new ErrorInterceptor())
                .build();
    }

    @Override
    public Retrofit.Builder getRetrofitBuilder() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        return new Retrofit.Builder().baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson));
    }

    @Override
    public String getTag() {
        return String.valueOf(hashCode()) + "_" + baseUrl;
    }

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

}