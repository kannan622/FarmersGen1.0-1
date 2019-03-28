package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class SignUpDTO {

    @SerializedName("mobile")
    String mobile;
    @SerializedName("name")
    String name;
    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;
    @SerializedName("device_id")
    String signup_DeviceID;

    public SignUpDTO(String mobile, String name, String email, String password, String signup_DeviceID) {
        this.mobile = mobile;
        this.name = name;
        this.email = email;
        this.password = password;
        this.signup_DeviceID = signup_DeviceID;
    }

    public String getMobile() {
        return mobile;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSignup_DeviceID() {
        return signup_DeviceID;
    }
}
