package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientToADDAddress implements BaseUrl {

    private static Retrofit retrofit=null;

    private static  Retrofit getAPIClientToADDAddress(){
        retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_TO_ADD_ADDRESS).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }

    public static ApiInterface getAPIInterfaceForADDAddress(){
        ApiInterface api=APIClientToADDAddress.getAPIClientToADDAddress().create(ApiInterface.class);
        return api;
    }
}
