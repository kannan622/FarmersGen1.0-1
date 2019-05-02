package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.example.saravanamurali.farmersgen.models.ReviewDetailsDTO;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonResponseForBrandReview {

    @SerializedName("records")
    List<ReviewDetailsDTO> reviewDetailsDTOS;

    public List<ReviewDetailsDTO> getReviewDetailsDTOS() {
        return reviewDetailsDTOS;
    }
}
