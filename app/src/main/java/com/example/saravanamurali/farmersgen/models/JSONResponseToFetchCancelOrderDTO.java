package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JSONResponseToFetchCancelOrderDTO {
    @SerializedName("records")
    List<JSONResponseForCancelOrderDTO> jsonResponseForCancelOrderDTO;

    public List<JSONResponseForCancelOrderDTO> getJsonResponseForCancelOrderDTO() {
        return jsonResponseForCancelOrderDTO;
    }
}
