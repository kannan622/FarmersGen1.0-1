package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class ReviewDTO {

    @SerializedName("brand_id")
    String brandID;

    public ReviewDTO(String brandID) {
        this.brandID = brandID;
    }

    public String getBrandID() {
        return brandID;
    }
}
