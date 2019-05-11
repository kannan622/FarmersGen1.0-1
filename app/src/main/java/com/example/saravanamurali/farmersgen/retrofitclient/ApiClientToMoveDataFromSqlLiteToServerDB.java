package com.example.saravanamurali.farmersgen.retrofitclient;

import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.baseurl.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientToMoveDataFromSqlLiteToServerDB {

    private static Retrofit retrofit=null;

    private static Retrofit  APIClientToMoveDataFromSqlLiteToServerDB() {

        retrofit=new Retrofit.Builder().baseUrl(BaseUrl.ROOT_URL_TO_MOVE_SQLLITE_TO_SERVER_DB).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }

    public static ApiInterface getAPIInterfaceToMoveDataFromSqlLiteToServerDB(){

        ApiInterface api=ApiClientToMoveDataFromSqlLiteToServerDB.APIClientToMoveDataFromSqlLiteToServerDB().create(ApiInterface.class);

        return api;
    }


}
