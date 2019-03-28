package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientToCancelOrderUsingOrderID implements BaseUrl {

    static Retrofit retrofit=null;

    private static Retrofit getAPIClientToCancelOrderUsingOrderID(){

        retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_TO_GET_CANCEL_ORDER).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }

    public static ApiInterface getAPIInterfaceToCancelOrderUsingOrderID(){

        ApiInterface api=APIClientToCancelOrderUsingOrderID.getAPIClientToCancelOrderUsingOrderID().create(ApiInterface.class);
        return api;

        }
}
