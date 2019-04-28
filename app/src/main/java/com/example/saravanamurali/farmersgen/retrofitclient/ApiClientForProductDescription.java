package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientForProductDescription implements BaseUrl {

    public static Retrofit retrofit=null;

    private static Retrofit getApiClientForProductDescription(){
        if(retrofit==null){

            retrofit = new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_FOR_PRODUCT_DESC).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }

    public static ApiInterface getApiInterfaceForProdictDesctiption() {

        ApiInterface api = ApiClientForProductDescription.getApiClientForProductDescription().create(ApiInterface.class);

        return api;

    }

}
