package com.example.ubees.model;

public class Place_Order {

    String total_amount;
    String refrence_num;
    String status;

    public Place_Order(String total_amount, String refrence_num, String status) {
        this.total_amount = total_amount;
        this.refrence_num = refrence_num;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Place_Order(){}


    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getRefrence_num() {
        return refrence_num;
    }

    public void setRefrence_num(String refrence_num) {
        this.refrence_num = refrence_num;
    }

}
