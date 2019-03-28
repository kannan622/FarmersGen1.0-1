package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientForUpdateCountInMenuCartFragment implements BaseUrl {

    public static Retrofit retrofit=null;

    private static Retrofit getAPIClientForUpdateCountInMenuCartFragment(){

        retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_TO_UPDATE_COUNT_IN_MENU_CART_FRAGMENT).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }

    public static ApiInterface getAPIInterfaceToUpdateCountInMenuCartFragment(){

        ApiInterface api=APIClientForUpdateCountInMenuCartFragment.getAPIClientForUpdateCountInMenuCartFragment().create(ApiInterface.class);

        return api;
    }
}
