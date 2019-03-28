package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientToGetCoupon implements BaseUrl {

    static Retrofit retrofit=null;

    private static Retrofit getApiClientToGetCoupon(){

        retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_TO_GET_COUPON).addConverterFactory(GsonConverterFactory.create()).build();

        return  retrofit;
    }

    public  static ApiInterface getApiInterfaceToGetCoupon(){

        ApiInterface apiInterface=APIClientToGetCoupon.getApiClientToGetCoupon().create(ApiInterface.class);

        return  apiInterface;
    }
}
