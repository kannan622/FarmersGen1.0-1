package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientForUpdateCountInViewCart implements BaseUrl {

    static Retrofit retrofit=null;

    public static Retrofit getAPIClientForUpdateCountInViewCart(){

        if(retrofit==null){

            retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_FOR_UPDATE_COUNT_IN_ADDCART).addConverterFactory(GsonConverterFactory.create()).build();

        }

        return retrofit;
    }

    public static ApiInterface getAPIClientForUpdateCountInViewCartInterface(){

        ApiInterface api=APIClientForUpdateCountInViewCart.getAPIClientForUpdateCountInViewCart().create(ApiInterface.class);
        return api;

    }
}
