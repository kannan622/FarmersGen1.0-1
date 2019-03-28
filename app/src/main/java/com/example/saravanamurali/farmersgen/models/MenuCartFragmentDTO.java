package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class MenuCartFragmentDTO {

    @SerializedName("device_id")
    String deviceID;

    public MenuCartFragmentDTO(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceID() {
        return deviceID;
    }
}
