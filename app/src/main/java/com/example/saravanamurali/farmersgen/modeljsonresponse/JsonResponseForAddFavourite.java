package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class JsonResponseForAddFavourite {

    @SerializedName("Data")
    Data data;

    @SerializedName("status")
    int fav_added_status;


    class Data{

        @SerializedName("id")
        String id;
        @SerializedName("user_id")
        String user_Id;

        @SerializedName("brand_id")
        String brand_Id;

        @SerializedName("favorite_status")
        String fav_status;

        public String getId() {
            return id;
        }

        public String getUser_Id() {
            return user_Id;
        }

        public String getBrand_Id() {
            return brand_Id;
        }

        public String getFav_status() {
            return fav_status;
        }
    }

    public Data getData() {
        return data;
    }

    public int getFav_added_status() {
        return fav_added_status;
    }
}
