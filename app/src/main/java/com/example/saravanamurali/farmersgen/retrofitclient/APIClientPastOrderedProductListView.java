package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientPastOrderedProductListView {

    static Retrofit retrofit=null;

    private  static Retrofit getAPIClientPastOrderedProductListView(){
        retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_TO_FETCH_PAST_ORDERED_PRODUCT_DETAILS).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }

    public static ApiInterface getApiInterfacePastOrderedProductListView(){

        ApiInterface apiInterface=APIClientPastOrderedProductListView.getAPIClientPastOrderedProductListView().create(ApiInterface.class);
        return  apiInterface;

    }
}
