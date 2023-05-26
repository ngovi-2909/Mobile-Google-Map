package tdtu.edu.vn.musicapp.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIRetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String base_url) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(10000, TimeUnit.MILLISECONDS) //server khong reply thi ngat ket noi
                .writeTimeout(10000, TimeUnit.MILLISECONDS)
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true) //bi loi ve mang thi se co gang ket noi
                .protocols(Arrays.asList(Protocol.HTTP_1_1)) //do bi loi khi khong tim duoc giao thuc http, https
                .build();
        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson)) //conver data to value of java
                .build();

        // return nguoi dung
        return  retrofit;
    }


}