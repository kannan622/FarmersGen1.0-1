package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class UpdateAddressDTO {

    @SerializedName("address")
    String address;
    @SerializedName("flat_no")
    String upateFlatNo;
    @SerializedName("pincode")
    String pincode;
    @SerializedName("alternative_mobile_no")
    String alternateMobileNumber;
    @SerializedName("user_id")
    String user_id;
    @SerializedName("address_id")
    String address_id;
    @SerializedName("latitude")
    Double lat;
    @SerializedName("longitute")
    Double longi;


    public UpdateAddressDTO(String address, String upateFlatNo, String pincode, String alternateMobileNumber, String user_id, String address_id, Double lat, Double longi) {
        this.address = address;
        this.upateFlatNo = upateFlatNo;
        this.pincode = pincode;
        this.alternateMobileNumber = alternateMobileNumber;
        this.user_id = user_id;
        this.address_id = address_id;
        this.lat = lat;
        this.longi = longi;
    }

    public String getAddress() {
        return address;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLongi() {
        return longi;
    }

    @SerializedName("street_name")
    String streetName;
    @SerializedName("area")
    String area;
    @SerializedName("city")
    String city;


    @SerializedName("landmark")
    String landMark;



    public UpdateAddressDTO(String upateFlatNo, String streetName, String area, String city, String pincode, String address_id,String landmark,String alternateMobile_Number, String user_id) {
        this.upateFlatNo = upateFlatNo;
        this.streetName = streetName;
        this.area = area;
        this.city = city;
        this.pincode = pincode;
        this.address_id = address_id;
        this.landMark=landmark;
        this.alternateMobileNumber=alternateMobile_Number;
        this.user_id = user_id;
    }


    public String getLandMark() {
        return landMark;
    }

    public String getAlternateMobileNumber() {
        return alternateMobileNumber;
    }

    public String getUpateFlatNo() {
        return upateFlatNo;
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

    public String getAddress_id() {
        return address_id;
    }

    public String getUser_id() {
        return user_id;
    }
}
