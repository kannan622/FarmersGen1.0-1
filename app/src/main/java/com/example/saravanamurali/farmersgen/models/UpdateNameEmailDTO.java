package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class UpdateNameEmailDTO {

    @SerializedName("user_id")
    String userId;
    @SerializedName("name")
    String u_name;
    @SerializedName("email")
    String u_email;


    public UpdateNameEmailDTO() {
    }

    public UpdateNameEmailDTO( String u_name, String u_email, String userId) {
        this.u_name = u_name;
        this.u_email = u_email;
        this.userId = userId;
    }


    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
