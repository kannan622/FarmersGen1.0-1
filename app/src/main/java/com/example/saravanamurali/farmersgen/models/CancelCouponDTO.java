package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class CancelCouponDTO {

    @SerializedName("off_userid")
    String userID;
    @SerializedName("coupon_id")
    String couponID;

    public CancelCouponDTO(String userID, String couponID) {
        this.userID = userID;
        this.couponID = couponID;
    }

    public String getUserID() {
        return userID;
    }

    public String getCouponID() {
        return couponID;
    }
}
