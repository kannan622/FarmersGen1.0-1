package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientToViewCartFromMenuCartFragment implements BaseUrl {

    private static Retrofit retrofit=null;

    private static Retrofit getAPIClientToViewCartFromMenuCartFragment(){

        retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_TO_VIEWCART_AT_MENU_CART_FRAGMENT).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }

    public static ApiInterface getApiInterfaceToViewCartFromMenuCartFragment(){
        ApiInterface apiInterface =APIClientToViewCartFromMenuCartFragment.getAPIClientToViewCartFromMenuCartFragment().create(ApiInterface.class);

        return apiInterface;
    }
}
