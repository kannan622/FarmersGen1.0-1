package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class JSONResponseADDAddress {

    @SerializedName("Status")
    int resultStatus;

    public int getResultStatus() {
        return resultStatus;
    }
}
