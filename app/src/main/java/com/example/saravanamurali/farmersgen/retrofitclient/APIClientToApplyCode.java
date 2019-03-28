package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientToApplyCode implements BaseUrl {

    static Retrofit retrofit=null;

    private static Retrofit getAPIClientToApplyCode(){

        retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_TO_APPLY_COUPON).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }

    public static ApiInterface getApiInterfaceToApplyCode(){

        ApiInterface apiInterface=APIClientToApplyCode.getAPIClientToApplyCode().create(ApiInterface.class);

        return apiInterface;
    }
}
