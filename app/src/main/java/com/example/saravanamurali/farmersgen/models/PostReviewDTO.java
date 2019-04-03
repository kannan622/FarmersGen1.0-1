package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class PostReviewDTO {

    @SerializedName("user_id")
    String user_ID;

    @SerializedName("brand_id")
    String brandId;

    @SerializedName("review_text")
    String reviewText;

    public PostReviewDTO(String user_ID, String brandId, String reviewText) {
        this.user_ID = user_ID;
        this.brandId = brandId;
        this.reviewText = reviewText;
    }

    public String getUser_ID() {
        return user_ID;
    }

    public String getBrandId() {
        return brandId;
    }

    public String getReviewText() {
        return reviewText;
    }
}

