package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientToPostReview implements BaseUrl {

    static Retrofit retrofit=null;

    private static Retrofit getApiClientToPostReview(){

        retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_TO_POST_BRAND_REVIEW).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;

    }

    public static ApiInterface getApiInterfaceToPostReview(){

        ApiInterface api=ApiClientToPostReview.getApiClientToPostReview().create(ApiInterface.class);
        return  api;
    }

}
