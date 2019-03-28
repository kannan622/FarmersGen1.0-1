package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientToSignUp implements BaseUrl {

    public static Retrofit retrofit = null;

    private static Retrofit getAPIClientToSignUp() {

        retrofit = new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_FOR_SIGNUP).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }

    public static ApiInterface getApiInterface() {

        ApiInterface api = ApiClientToSignUp.getAPIClientToSignUp().create(ApiInterface.class);

        return api;

    }


}
