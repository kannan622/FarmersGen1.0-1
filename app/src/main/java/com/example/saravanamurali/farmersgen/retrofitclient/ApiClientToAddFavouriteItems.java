package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientToAddFavouriteItems {

    static Retrofit retrofit=null;

    private static Retrofit getAPIClientToAddFav(){

        retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_TO_ADD_FAVOURITE).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }

    public static ApiInterface getApiInterfaceAddFavouriteItem(){

        ApiInterface apiInterface=ApiClientToAddFavouriteItems.getAPIClientToAddFav().create(ApiInterface.class);

        return apiInterface;
    }

}
