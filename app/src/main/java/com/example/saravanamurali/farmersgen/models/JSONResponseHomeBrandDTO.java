package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JSONResponseHomeBrandDTO {


    @SerializedName("records")
     List<HomeProductDTO> records;

    public List<HomeProductDTO> getRecords() {
        return records;
    }
}
