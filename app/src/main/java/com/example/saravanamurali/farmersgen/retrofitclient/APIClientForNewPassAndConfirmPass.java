package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientForNewPassAndConfirmPass implements BaseUrl {

    private static Retrofit retrofit=null;

    private static Retrofit getAPIClientForNewPassAndConfirmPass(){

        retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_FOR_NEWPASS_CONFIRMPASS).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }

    public  static ApiInterface getApiInterfaceForNewPasswordAndConfirmPassword(){

        ApiInterface api=APIClientForNewPassAndConfirmPass.getAPIClientForNewPassAndConfirmPass().create(ApiInterface.class);

        return api;
    }
}
