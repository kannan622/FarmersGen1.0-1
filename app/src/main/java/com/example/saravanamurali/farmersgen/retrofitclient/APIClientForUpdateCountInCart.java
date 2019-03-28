package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

 public class APIClientForUpdateCountInCart implements BaseUrl {

    public static Retrofit retrofit=null;

    public static Retrofit getApiClientForUpdateCountInAddCart(){

        if(retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_FOR_UPDATE_COUNT_IN_ADDCART)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }

        return  retrofit;
    }

    public static ApiInterface getApiInterfaceForUpdateCountInAddCart(){

        ApiInterface api=APIClientForUpdateCountInCart.getApiClientForUpdateCountInAddCart().create(ApiInterface.class);

        return api ;

    }


}
