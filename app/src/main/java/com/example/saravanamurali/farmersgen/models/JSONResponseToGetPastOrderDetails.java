package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JSONResponseToGetPastOrderDetails {

    @SerializedName("records")
    List<JSONResponseToGetPastOrderDTO> jsonResponseToGetPastOrderDTOList;

    public List<JSONResponseToGetPastOrderDTO> getJsonResponseToGetPastOrderDTOList() {
        return jsonResponseToGetPastOrderDTOList;
    }
}
