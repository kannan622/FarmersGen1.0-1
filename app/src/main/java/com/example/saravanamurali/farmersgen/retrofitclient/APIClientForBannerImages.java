package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientForBannerImages implements BaseUrl {

    public static Retrofit retrofit=null;

    public static Retrofit getRetrofitClientForBannerImages(){

        if(retrofit==null) {

            retrofit = new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_FOR_BANNER_IMAGES).addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit;
    }


    public static ApiInterface getApiInterfaceForBannerImages() {

        ApiInterface api = APIClientForBannerImages.getRetrofitClientForBannerImages().create(ApiInterface.class);

        return api;

    }


    }
