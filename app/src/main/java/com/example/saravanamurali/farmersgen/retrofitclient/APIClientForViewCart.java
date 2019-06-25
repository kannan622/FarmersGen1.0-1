package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.MapTypeAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientForViewCart implements BaseUrl {

    private static Retrofit retrofit = null;
    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();


    public static Retrofit getAPIClientForViewCart() {

        if (retrofit == null) {

            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();



            retrofit = new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_FOR_VIEWCART)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
        }

        return retrofit;
    }

    public static ApiInterface getApiInterfaceForViewCart() {

        ApiInterface api = APIClientForViewCart.getAPIClientForViewCart().create(ApiInterface.class);

        return api;

    }


}
