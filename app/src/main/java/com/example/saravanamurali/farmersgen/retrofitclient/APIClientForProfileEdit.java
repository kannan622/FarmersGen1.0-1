package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientForProfileEdit implements BaseUrl {

    static Retrofit retrofit=null;

    private static Retrofit getAPIClientForProfileEdit(){

        retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_TO_EDIT_PROFILE).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;

    }

    public  static ApiInterface getApiInterfaceToEditProfile(){

        ApiInterface api=APIClientForProfileEdit.getAPIClientForProfileEdit().create(ApiInterface.class);

        return api;
    }
}
