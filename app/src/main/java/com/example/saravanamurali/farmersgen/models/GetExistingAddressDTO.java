package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class GetExistingAddressDTO {

    @SerializedName("user_id")
    String currentUser_ID;

    public GetExistingAddressDTO(String currentUser_ID) {
        this.currentUser_ID = currentUser_ID;
    }

    public String getCurrentUser_ID() {
        return currentUser_ID;
    }

    public void setCurrentUser_ID(String currentUser_ID) {
        this.currentUser_ID = currentUser_ID;
    }
}
