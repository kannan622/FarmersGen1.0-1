package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientOrderedProductListView implements BaseUrl {

    static Retrofit retrofit=null;

    private  static Retrofit getAPIClientOrderedProductListView(){
        retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_TO_FETCH_ORDERED_PRODUCT_DETAILS).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }

    public static ApiInterface getgetApiInterfaceOrderedProductListView(){

        ApiInterface apiInterface=APIClientOrderedProductListView.getAPIClientOrderedProductListView().create(ApiInterface.class);
        return  apiInterface;

    }
}
