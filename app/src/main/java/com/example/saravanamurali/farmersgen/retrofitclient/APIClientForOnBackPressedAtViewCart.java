package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientForOnBackPressedAtViewCart implements BaseUrl {

    private static Retrofit retrofit=null;

    private static Retrofit getAPIClientForOnBackPressedAtViewCart(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_FOR_ON_BACK_PRESSED_AT_VIEWCART).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }

public  static ApiInterface getApiInterfaceForOnBackPressedAtViewCart(){
        ApiInterface api=APIClientForOnBackPressedAtViewCart.getAPIClientForOnBackPressedAtViewCart().create(ApiInterface.class);

        return api;
}
}
