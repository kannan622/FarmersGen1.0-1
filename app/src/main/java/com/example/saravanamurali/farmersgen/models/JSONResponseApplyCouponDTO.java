package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class JSONResponseApplyCouponDTO {

    @SerializedName("")
    String responseCode;

    @SerializedName("")
    String coupon_Code;

    @SerializedName("")
    String coupon_ID;

    public JSONResponseApplyCouponDTO(String responseCode, String coupon_Code, String coupon_ID) {
        this.responseCode = responseCode;
        this.coupon_Code = coupon_Code;
        this.coupon_ID = coupon_ID;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String getCoupon_Code() {
        return coupon_Code;
    }

    public String getCoupon_ID() {
        return coupon_ID;
    }
}
