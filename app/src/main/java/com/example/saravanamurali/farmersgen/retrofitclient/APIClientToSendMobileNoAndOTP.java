package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientToSendMobileNoAndOTP implements BaseUrl {

    private static Retrofit retrofit=null;

    private  static Retrofit getAPIClientToSendMobileNoAndOTP(){

        retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_TO_SEND_MOBILENO_AND_OTP).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;

    }

    public static ApiInterface getApiInterfaceToSendMobileNoAndOTP(){

        ApiInterface api=APIClientToSendMobileNoAndOTP.getAPIClientToSendMobileNoAndOTP().create(ApiInterface.class);
        return api;
    }
}
