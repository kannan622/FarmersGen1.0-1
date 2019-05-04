package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientToCheckFavourite implements BaseUrl {

    static Retrofit retrofit=null;

    private static Retrofit getApiClientToCheckFavourite(){
        retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_TO_CHECK_FAVOURITE).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }

    public static ApiInterface getApiInterfaceToCheckFavourite(){

        ApiInterface api=ApiClientToCheckFavourite.getApiClientToCheckFavourite().create(ApiInterface.class);

        return api;
    }
}
