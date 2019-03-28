package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientToUpdateAddress implements BaseUrl {

    private static Retrofit retrofit = null;

    private static Retrofit getAPIClientToUpdateAddress() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_TO_UPDATE_ADDRESS).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

    public static ApiInterface getApiIterfaceToUpdateAddress(){

        ApiInterface api=APIClientToUpdateAddress.getAPIClientToUpdateAddress().create(ApiInterface.class);

        return  api;
    }
}
