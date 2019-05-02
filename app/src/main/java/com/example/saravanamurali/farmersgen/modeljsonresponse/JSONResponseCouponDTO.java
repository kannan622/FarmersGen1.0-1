package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.example.saravanamurali.farmersgen.models.CouponDTO;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JSONResponseCouponDTO {

    @SerializedName("records")
    List<CouponDTO> jsonResponseCouponDTO;

    public List<CouponDTO> getJsonResponseCouponDTO() {
        return jsonResponseCouponDTO;
    }
}
