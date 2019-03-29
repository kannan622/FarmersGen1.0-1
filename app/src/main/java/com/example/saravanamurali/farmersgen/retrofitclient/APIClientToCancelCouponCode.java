package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientToCancelCouponCode implements BaseUrl {

    static Retrofit retrofit=null;

    private static Retrofit getApiClientToCancelCouponCode(){
        retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_TO_CANCEL_COUPON).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }

    public  static ApiInterface getApiInterfaceToCancelCouponCode(){

        ApiInterface api=APIClientToCancelCouponCode.getApiClientToCancelCouponCode().create(ApiInterface.class);
        return api;
    }
}
