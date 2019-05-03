package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class AddFavouriteDTO {

    @SerializedName("user_id")
    String user_id;
    @SerializedName("brand_id")
    String brandId;
    @SerializedName("favorite_status")
    String fav_status;

    public AddFavouriteDTO(String user_id, String brandId, String fav_status) {
        this.user_id = user_id;
        this.brandId = brandId;
        this.fav_status = fav_status;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getBrandId() {
        return brandId;
    }

    public String getFav_status() {
        return fav_status;
    }
}
