package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignedInJSONResponse {

    @SerializedName("user_id")
    String user_ID;
    @SerializedName("name")
    String name;
    @SerializedName("mobile")
    String mobile;
    @SerializedName("email")
    String email;

    @SerializedName("responsecode")
    int responseCode;

    @SerializedName("status")
    int status;

    public int getStatus() {
        return status;
    }

    public int getResponseCode() {
        return responseCode;
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
