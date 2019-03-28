package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientLogOutUsingDeviceID implements BaseUrl {

    private static Retrofit retrofit=null;

    private static Retrofit getAPIClientLogOutUsingDeviceID(){

        if(retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_TO_LOGOUT_USING_DEVICEID).addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit;
        }

        public  static ApiInterface getApiInterfaceToLogOutUsingDeviceID(){

        ApiInterface api=APIClientLogOutUsingDeviceID.getAPIClientLogOutUsingDeviceID().create(ApiInterface.class);
        return api;
        }


}
