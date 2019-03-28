package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class LogOutDeviceIDDTO {
    @SerializedName("device_id")
    String deviceID;

    public LogOutDeviceIDDTO(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceID() {
        return deviceID;
    }
}
