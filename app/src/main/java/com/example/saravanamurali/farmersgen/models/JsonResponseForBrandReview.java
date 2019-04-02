package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonResponseForBrandReview {

    @SerializedName("records")
    List<ReviewDetailsDTO> reviewDetailsDTOS;

    public List<ReviewDetailsDTO> getReviewDetailsDTOS() {
        return reviewDetailsDTOS;
    }
}
