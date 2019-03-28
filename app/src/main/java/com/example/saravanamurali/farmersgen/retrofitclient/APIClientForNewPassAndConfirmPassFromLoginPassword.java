package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientForNewPassAndConfirmPassFromLoginPassword {

    private static Retrofit retrofit=null;

    private static Retrofit getAPIClientForNewPassAndConfirmPassFromLoginPassword(){

        retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_FOR_NEWPASS_CONFIRMPASS).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }

    public  static ApiInterface getApiInterfaceForNewPassAndConfirmPassFromLoginPassword(){

        ApiInterface api=APIClientForNewPassAndConfirmPassFromLoginPassword.getAPIClientForNewPassAndConfirmPassFromLoginPassword().create(ApiInterface.class);

        return api;
    }
}
