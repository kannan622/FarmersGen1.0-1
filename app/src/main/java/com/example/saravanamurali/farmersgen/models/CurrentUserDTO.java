package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class CurrentUserDTO {

    @SerializedName("user_id")
    String currentUser;

    public CurrentUserDTO(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getCurrentUser() {
        return currentUser;
    }
}
