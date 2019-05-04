package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientToRemoveFav {

    private static Retrofit retrofit = null;

    private static Retrofit getAPIClientToRemoveFavBrands() {

        retrofit = new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_TO_REMOVE_FAVOURITE).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;

    }

    public static ApiInterface getApiInterfaceToRemoveBrands(){
        ApiInterface apiInterface=ApiClientToRemoveFav.getAPIClientToRemoveFavBrands().create(ApiInterface.class);
        return apiInterface;

    }

}
