package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientToGetExistingAddress implements BaseUrl {

    public static Retrofit retrofit=null;

    public static Retrofit  getAPIClientToGetExistingAddress(){

        retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_TO_GET_EXISTING_ADDRESS).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;

    }

    public static ApiInterface getAPIInterfaceTOGetExistingAddress(){

        ApiInterface api=APIClientToGetExistingAddress.getAPIClientToGetExistingAddress().create(ApiInterface.class);

        return api;

    }


}
