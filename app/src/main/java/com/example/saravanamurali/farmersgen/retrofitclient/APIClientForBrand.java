package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientForBrand implements BaseUrl {

    public static Retrofit retrofit=null;

    public static Retrofit getRetrofitClientForBrand(){

        if(retrofit==null) {

            retrofit = new Retrofit.Builder().baseUrl(ROOT_URL_BRAND_PRODUCTS).addConverterFactory(GsonConverterFactory.create()).build();

        }

        return retrofit;
    }

    public static ApiInterface getApiInterfaceForBrand() {

        ApiInterface api = APIClientForBrand.getRetrofitClientForBrand().create(ApiInterface.class);

        return api;

    }
}
