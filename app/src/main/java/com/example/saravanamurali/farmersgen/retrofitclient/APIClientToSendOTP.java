package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientToSendOTP implements BaseUrl {

    private static Retrofit retrofit = null;

    private static Retrofit getAPIClientToSendOTP() {

        if (retrofit == null) {

            retrofit = new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_TO_SEND_OTP).addConverterFactory(GsonConverterFactory.create()).build();

        }

        return retrofit;
    }

    public static ApiInterface getApiInterfaceToSendOTP() {

        ApiInterface api = APIClientToSendOTP.getAPIClientToSendOTP().create(ApiInterface.class);

        return api;
    }

}
