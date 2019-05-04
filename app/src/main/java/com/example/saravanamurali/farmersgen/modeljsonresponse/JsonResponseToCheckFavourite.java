package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class JsonResponseToCheckFavourite {

    @SerializedName("favorite_status")
    int fav_status;
    @SerializedName("status")
    int checkStatus;
    @SerializedName("user_id")
    String userId;
    @SerializedName("brand_id")
    String brandId;
    @SerializedName("responsecode")
    int responseCode;

    public int getFav_status() {
        return fav_status;
    }

    public int getCheckStatus() {
        return checkStatus;
    }

    public String getUserId() {
        return userId;
    }

    public String getBrandId() {
        return brandId;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
