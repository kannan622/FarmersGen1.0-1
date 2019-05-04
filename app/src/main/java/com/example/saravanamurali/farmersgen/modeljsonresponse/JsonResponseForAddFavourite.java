package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class JsonResponseForAddFavourite {


    @SerializedName("status")
    int fav_added_status;



    public int getFav_added_status() {
        return fav_added_status;
    }
}
