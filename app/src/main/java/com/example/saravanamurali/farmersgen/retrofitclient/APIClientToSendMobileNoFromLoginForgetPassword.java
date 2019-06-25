package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientToSendMobileNoFromLoginForgetPassword {

    private static Retrofit retrofit=null;

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();


    private static  Retrofit getAPIClientToSendMobileNoFromLoginForgetPassword(){

        if(retrofit==null){

            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();


            retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_TO_SEND_OTP_FROM_FORGET_PASSWORD)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();

        }
        return retrofit;

    }

    public  static ApiInterface getAPIInterfaceTOSendMobileNoFromLoginForgetPassword(){

        ApiInterface api=APIClientToSendMobileNoFromLoginForgetPassword.getAPIClientToSendMobileNoFromLoginForgetPassword().create(ApiInterface.class);

        return  api;
    }
}
