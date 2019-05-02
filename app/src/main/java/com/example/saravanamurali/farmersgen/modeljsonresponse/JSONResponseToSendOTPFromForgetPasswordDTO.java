package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class JSONResponseToSendOTPFromForgetPasswordDTO {
    @SerializedName("Status")
    String status;
    @SerializedName("Message")
    String Message;

}
