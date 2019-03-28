package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class SignInDTO {

    @SerializedName("mobile")
    String loginUserName;
    @SerializedName("password")
    String loginPassword;


    public SignInDTO(String loginUserName, String loginPassword) {
        this.loginUserName = loginUserName;
        this.loginPassword = loginPassword;
    }

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
}
