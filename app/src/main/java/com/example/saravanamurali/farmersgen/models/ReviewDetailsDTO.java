package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class ReviewDetailsDTO {

    @SerializedName("name")
    String reviewUser;

    @SerializedName("review_text")
    String reviewMessage;

    public String getReviewUser() {
        return reviewUser;
    }

    public String getReviewMessage() {
        return reviewMessage;
    }


}
