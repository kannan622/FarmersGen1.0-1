package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class JSONResponseApplyCouponDTO {

    @SerializedName("success")
    Success success;

    @SerializedName("off_userid")
    String userID;

    @SerializedName("coupon_id")
    String coupon_ID;

    @SerializedName("off_code")
    String coupon_Code;

    public Success getSuccess() {
        return success;
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

   public class Success{

        @SerializedName("response_code")
        String responseCode;
        @SerializedName("message")
        String message;

        public String getResponseCode() {
            return responseCode;
        }

        public String getMessage() {
            return message;
        }
    }

}
