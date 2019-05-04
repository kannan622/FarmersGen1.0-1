package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class CheckFavDTO {

    @SerializedName("brand_id")
    String brandId;
    @SerializedName("user_id")
    String user_id;

    public CheckFavDTO(String brandId, String user_id) {
        this.brandId = brandId;
        this.user_id = user_id;
    }

    public String getBrandId() {
        return brandId;
    }

    public String getUser_id() {
        return user_id;
    }
}
