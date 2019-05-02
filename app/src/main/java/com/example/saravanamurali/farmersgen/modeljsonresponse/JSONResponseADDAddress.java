package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class JSONResponseADDAddress {

    @SerializedName("Status")
    int resultStatus;

    public int getResultStatus() {
        return resultStatus;
    }
}
