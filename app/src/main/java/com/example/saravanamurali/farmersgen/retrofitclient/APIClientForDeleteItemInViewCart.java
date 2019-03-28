package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientForDeleteItemInViewCart implements BaseUrl {

    static Retrofit retrofit=null;

    public static Retrofit getAPIClientForDeleteItemViewCart(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_FOR_DELETE_ITEM_FROM_CART).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }

    public static ApiInterface getApiInterfaceForDeleteItemFromViewCart(){

        ApiInterface api=APIClientForDeleteItemInViewCart.getAPIClientForDeleteItemViewCart().create(ApiInterface.class);

        return  api;
    }
}
