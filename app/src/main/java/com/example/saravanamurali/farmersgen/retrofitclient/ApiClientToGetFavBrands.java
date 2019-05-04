package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientToGetFavBrands implements BaseUrl {

    private static Retrofit retrofit = null;

    private static Retrofit getAPIClientToGetFavBrands() {

        retrofit = new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_TO_GET_FAVOURITE).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;

    }

    public static ApiInterface getApiInterfaceToGetFavBrands() {
        ApiInterface apiInterface = ApiClientToGetFavBrands.getAPIClientToGetFavBrands().create(ApiInterface.class);
        return apiInterface;
    }
}
