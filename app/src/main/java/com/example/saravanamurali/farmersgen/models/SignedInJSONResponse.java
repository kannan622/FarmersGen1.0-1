package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignedInJSONResponse {

    @SerializedName("success")
    Success success;
    @SerializedName("user_id")
    String user_ID;
    @SerializedName("name")
    String name;
    @SerializedName("mobile")
    String mobile;
    @SerializedName("email")
    String email;

    public class Success {
        @SerializedName("response code")
        String responseCode;
        @SerializedName("status")
        int status;
        @SerializedName("message")
        String message;


    }

    public Success getSuccess() {
        return success;
    }

    public String getUser_ID() {
        return user_ID;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }
}
