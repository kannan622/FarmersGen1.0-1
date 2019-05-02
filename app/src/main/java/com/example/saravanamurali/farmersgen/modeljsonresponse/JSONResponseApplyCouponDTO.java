package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class JSONResponseApplyCouponDTO {

    @SerializedName("response_code")
    int  responseCode ;

    @SerializedName("off_userid")
    String userID;

    @SerializedName("coupon_id")
    String coupon_ID;

    @SerializedName("off_code")
    String coupon_Code;


    public int getResponseCode() {
        return responseCode;
    }

    public String getUserID() {
        return userID;
    }

    public String getCoupon_ID() {
        return coupon_ID;
    }

    public String getCoupon_Code() {
        return coupon_Code;
    }
}
