package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientForProceedBtnInViewCart implements BaseUrl {

    static Retrofit retrofit=null;

    public static Retrofit getAPIClientForProceedBtnInViewCart(){

        if(retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_FOR_PROCEED_BTN_VIEWCART).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return  retrofit;
    }

    public static ApiInterface getApiInterfaceForProceedBtnInViewCart(){

        ApiInterface api=APIClientForProceedBtnInViewCart.getAPIClientForProceedBtnInViewCart().create(ApiInterface.class);

        return api;
    }
}
