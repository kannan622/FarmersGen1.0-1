package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class NewPassAndConfirmPassDTO {

    @SerializedName("mobile")
    String mobile;
    @SerializedName("new_password")
    String newPassword;
    @SerializedName("confirm_password")
    String confirmPassword;

    public NewPassAndConfirmPassDTO(String mobile, String newPassword, String confirmPassword) {
        this.mobile = mobile;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public String getMobile() {
        return mobile;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
