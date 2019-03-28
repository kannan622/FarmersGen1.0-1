package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientForCart implements BaseUrl {

    public static Retrofit retrofit=null;

    public static Retrofit getApiClientForCount(){

       /* Gson gson = new GsonBuilder()
                .setLenient()
                .create();
*/
        if(retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_FOR_ADDCART).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return  retrofit;
    }

    public static ApiInterface getApiInterfaceForCount(){

        ApiInterface api=APIClientForCart.getApiClientForCount().create(ApiInterface.class);

        return api ;

    }
}
