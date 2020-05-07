package com.example.gestordegastosfacub.network;

import com.example.gestordegastosfacub.BuildConfig;
import com.example.gestordegastosfacub.helpers.SessionPersistence;
import com.example.gestordegastosfacub.models.Category;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static Api API_SERVICE;
    private static Retrofit RETROFIT;
    private static OkHttpClient CLIENTE;
    private static OkHttpClient.Builder BUILDER = new OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20,TimeUnit.SECONDS)
            .writeTimeout(1,TimeUnit.MINUTES);

    public static Api getApiService() { return API_SERVICE; }

    public static Retrofit getRETROFIT(){ return RETROFIT; }

    public static OkHttpClient getCLIENTE(){ return CLIENTE; }

    public static OkHttpClient.Builder getBUILDER(){ return BUILDER; }

    public static Gson buildGson(){ return new GsonBuilder().create(); }

    public static void addBetterLogsToRequest(OkHttpClient.Builder okhttpBuilder){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpBuilder.addInterceptor(interceptor);
    }

    public static void addTokensToResquest(OkHttpClient.Builder okhttpClientBuilder){
        okhttpClientBuilder.addInterceptor(chain ->{
            Request original = chain.request();
            String token = SessionPersistence.getUser() == null ? "" :
                    SessionPersistence.getUser().getAuthToken();
            Request.Builder requesBuilder = original.newBuilder()
                .header("Authorization",token)
                .header("Content-Type","application/json");
            return chain.proceed(requesBuilder.build());
        });
    }

    public static OkHttpClient builOkhttp(){
        addTokensToResquest(getBUILDER());
        if(BuildConfig.DEBUG)addBetterLogsToRequest(getBUILDER());
        return getBUILDER().build();
    }

    public static void init(){
        Gson gson = buildGson();
        CLIENTE = builOkhttp();
        RETROFIT = new Retrofit.Builder()
                .baseUrl("http://dev.expenses.vortexsoftware.com.ar")
                .client(CLIENTE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        API_SERVICE = RETROFIT.create(Api.class);
    }

}
