package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientForPaymentGateway {

    static Retrofit retrofit=null;

    private static Retrofit getAPIClientForPaymentGateway(){

        retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_FOR_CARD_PAYMENT).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;

    }

    public static ApiInterface getApiInterfaceForCardPayment(){

        ApiInterface apiInterface=ApiClientForPaymentGateway.getAPIClientForPaymentGateway().create(ApiInterface.class);
        return apiInterface;
    }

}
