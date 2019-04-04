package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class ADDAddessDTO {
    @SerializedName("flat_no")
    String flatNo;
    @SerializedName("street_name")
    String streetName;
    @SerializedName("area")
    String area;
    @SerializedName("city")
    String city;
    @SerializedName("pincode")
    String pincode;
    @SerializedName("landmark")
    String landMark;
    @SerializedName("alternative_mobile_no")
    String alternateMobileNumber;
    @SerializedName("user_id")
    String user_ID;


    public ADDAddessDTO(String flatNo, String streetName, String area, String city, String pincode, String landMark,String alternateMobileNumber,String user_ID) {
        this.flatNo = flatNo;
        this.streetName = streetName;
        this.area = area;
        this.city = city;
        this.pincode = pincode;
        this.landMark=landMark;
        this.alternateMobileNumber=alternateMobileNumber;
        this.user_ID=user_ID;
    }

    public String getLandMark() {
        return landMark;
    }

    public String getAlternateMobileNumber() {
        return alternateMobileNumber;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getArea() {
        return area;
    }

    public String getCity() {
        return city;
    }

    public String getPincode() {
        return pincode;
    }

    public String getUser_ID() {
        return user_ID;
    }
}
