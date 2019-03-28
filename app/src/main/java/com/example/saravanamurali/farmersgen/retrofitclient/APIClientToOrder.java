package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class APIClientToOrder implements BaseUrl {

    private static Retrofit retrofit=null;

    private static Retrofit  APIClientToOrder() {

        retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_TO_ORDER).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }

    public static ApiInterface getAPIInterfaceToOrder(){

        ApiInterface api=APIClientToOrder.APIClientToOrder().create(ApiInterface.class);

        return api;
    }

    }
