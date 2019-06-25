package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class CouponDetailResponse {

    @SerializedName("response_code")
    int responseCode;
    @SerializedName("coupon_image")
    String coupon_image;
    @SerializedName("coupon_description1")
    String coupon_Description1;
    @SerializedName("coupon_description2")
    String coupon_Description2;
    @SerializedName("condition_1")
    String condition1;
    @SerializedName("condition_2")
    String condition2;
    @SerializedName("condition_3")
    String condition3;
    @SerializedName("condition_4")
    String condition4;
    @SerializedName("condition_5")
    String condition5;

    public int getResponseCode() {
        return responseCode;
    }

    public String getCoupon_image() {
        return coupon_image;
    }

    public String getCoupon_Description1() {
        return coupon_Description1;
    }

    public String getCoupon_Description2() {
        return coupon_Description2;
    }

    public String getCondition1() {
        return condition1;
    }

    public String getCondition2() {
        return condition2;
    }

    public String getCondition3() {
        return condition3;
    }

    public String getCondition4() {
        return condition4;
    }

    public String getCondition5() {
        return condition5;
    }
}
