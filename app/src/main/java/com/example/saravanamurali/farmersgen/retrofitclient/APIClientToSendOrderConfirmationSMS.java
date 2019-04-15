package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientToSendOrderConfirmationSMS implements BaseUrl {

    static Retrofit retrofit=null;

    private static Retrofit getApiClientToSendOrderConfirmationSMS(){

        if(retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_TO_SEND_ORDER_CONFIRMATION_SMS).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

    public static ApiInterface getApiInterfaceToSendOrderConfirmationSMS(){

        ApiInterface api=APIClientToSendOrderConfirmationSMS.getApiClientToSendOrderConfirmationSMS().create(ApiInterface.class);

        return api;
    }
}
