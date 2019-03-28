package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientToUpdateNameAndEmail implements BaseUrl {

    private static Retrofit retrofit=null;


    private static Retrofit getAPIClientToUpdateNameAndMail() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_TO_UPDATE_NAME_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

    public static ApiInterface getApiIterfaceToUpdateNameAndMail(){

        ApiInterface api=APIClientToUpdateNameAndEmail.getAPIClientToUpdateNameAndMail().create(ApiInterface.class);

        return  api;
    }

}
