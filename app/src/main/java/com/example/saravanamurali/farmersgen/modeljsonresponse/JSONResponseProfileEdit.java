package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class JSONResponseProfileEdit {

    @SerializedName("success")
    Success getSuccessDetails;

    @SerializedName("user_id")
    String userID;

    @SerializedName("mobile")
    String profileMobileNo;
    @SerializedName("name")
    String profileName;
    @SerializedName("email")
    String profileEmail;

    class Success{

        @SerializedName("responsecode")
        String responseCode;
        @SerializedName("status")
        String status;
        @SerializedName("message")
        String message;

        public String getResponseCode() {
            return responseCode;
        }

        public String getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }

    public Success getGetSuccessDetails() {
        return getSuccessDetails;
    }

    public String getUserID() {
        return userID;
    }

    public String getProfileMobileNo() {
        return profileMobileNo;
    }

    public String getProfileName() {
        return profileName;
    }

    public String getProfileEmail() {
        return profileEmail;
    }
}
