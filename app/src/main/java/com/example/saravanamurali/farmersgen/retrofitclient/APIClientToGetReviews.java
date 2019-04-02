package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientToGetReviews implements BaseUrl {

    static Retrofit retrofit=null;

    private static Retrofit getApiClientToGetReviews(){

        retrofit =new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_FOR_BRAND_REVIEW).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }

    public static ApiInterface getApiInterfaceToGetReviews(){

        ApiInterface api=APIClientToGetReviews.getApiClientToGetReviews().create(ApiInterface.class);
        return api;
    }

}
