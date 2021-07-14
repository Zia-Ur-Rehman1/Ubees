package com.example.ubees.model;

import java.util.ArrayList;

public class User {
    String user_type;
    String user_name;
    String f_name;
    String l_name;
    String city;
    String address;
    String phone;
    public  User(){}
    public User(String user_type, String user_name,  String f_name, String l_name, String city, String address, String phone) {
        this.user_type = user_type;
        this.user_name = user_name;
        this.f_name = f_name;
        this.l_name = l_name;
        this.city = city;
        this.address = address;
        this.phone = phone;
    }



    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
