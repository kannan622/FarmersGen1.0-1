package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientToSendOTPToMFrom_FP implements BaseUrl {

    private static Retrofit retrofit=null;

    private static  Retrofit getAPIClientToSendOTPToMobileFromForgetPassword(){

        if(retrofit==null){

            retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_TO_SEND_OTP_FROM_FORGET_PASSWORD).addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit;

    }

    public  static ApiInterface getAPIInterfaceTOSendOTPFrom_FP(){

        ApiInterface api=APIClientToSendOTPToMFrom_FP.getAPIClientToSendOTPToMobileFromForgetPassword().create(ApiInterface.class);

        return  api;
    }
}
