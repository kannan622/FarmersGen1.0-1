package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class GetDeliveryAddressDTO {

    @SerializedName("address_id")
    String addressID;
    @SerializedName("user_id")
    String userID;
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
    String landmar;

    @SerializedName("alternative_mobile_no")
    String alter;

    public GetDeliveryAddressDTO(String addressID, String userID, String flatNo, String streetName, String area, String city, String pincode,String landMark,String alternate) {
        this.addressID = addressID;
        this.userID = userID;
        this.flatNo = flatNo;
        this.streetName = streetName;
        this.area = area;
        this.city = city;
        this.pincode = pincode;
        this.landmar=landMark;
        this.alter=alternate;
    }

    public String getLandmar() {
        return landmar;
    }

    public String getAlter() {
        return alter;
    }

    public String getAddressID() {
        return addressID;
    }

    public void setAddressID(String addressID) {
        this.addressID = addressID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}


