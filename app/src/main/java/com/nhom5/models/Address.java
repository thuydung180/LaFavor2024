package com.nhom5.models;

import android.widget.AdapterView;

public class Address {
    String fullName;
    String phoneNumber;
    String province;
    String district;
    String ward;
    String street;

    AdapterView.OnItemLongClickListener longClickListener;
    public Address() {

    }

    public Address(String fullName, String phoneNumber, String province, String district, String ward, String street) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.street = street;
    }


    public void setLongClickListener(AdapterView.OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }




    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    public String setAddress() {
        return street + ", " + ward + ", " + district +", "+ province;
    }



}
