package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.example.saravanamurali.farmersgen.models.BannerDTO;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonResponseForBannerDTO {

    @SerializedName("records")
    List<BannerDTO> records;

    public List<BannerDTO> getRecords() {
        return records;
    }
}
