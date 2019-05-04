package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.example.saravanamurali.farmersgen.models.FavBrandDTO;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonResponseForFavBrandsDTO {

    @SerializedName("records")
    List<FavBrandDTO> getfavRecords;

    public List<FavBrandDTO> getFavRecords() {
        return getfavRecords;
    }
}
