package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class FcmTokenDTO {

    @SerializedName("user_id")
    private  String user_ID;
    @SerializedName("notification_token")
    private  String fcm_token;

    public FcmTokenDTO(String user_ID, String fcm_token) {
        this.user_ID = user_ID;
        this.fcm_token = fcm_token;
    }

    public String getUser_ID() {
        return user_ID;
    }

    public String getFcm_token() {
        return fcm_token;
    }
}
