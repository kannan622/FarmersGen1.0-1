package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JSONResponseToGetProductDescListDTO {

    @SerializedName("records")
    List<JSONResponseToGetProductDescDTO> jsonResponseToGetProductDescDTOList;

    public List<JSONResponseToGetProductDescDTO> getJsonResponseToGetProductDescDTOList() {
        return jsonResponseToGetProductDescDTOList;
    }
}
