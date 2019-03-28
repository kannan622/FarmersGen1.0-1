package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class SignUpJSONResponse {
    @SerializedName("Status")
    int resultStatus;

    @SerializedName("Message")
    String resultMessage;

    @SerializedName("Data")
    Data data;

    public class Data {
        @SerializedName("id")
        String id;

        @SerializedName("email")
        String mail;

        @SerializedName("password")
        String password;

        @SerializedName("otp")
        String otp;

        @SerializedName("device_id")
        String deviceId;

        @SerializedName("user_id")
        String userID;
        @SerializedName("name")
        String name;
        @SerializedName("mobile")
        String mobile;

        public String getId() {
            return id;
        }

        public String getMail() {
            return mail;
        }

        public String getPassword() {
            return password;
        }

        public String getOtp() {
            return otp;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public String getUserID() {
            return userID;
        }

        public String getName() {
            return name;
        }

        public String getMobile() {
            return mobile;
        }
    }

    public int getResultStatus() {
        return resultStatus;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public Data getData() {
        return data;
    }
}
