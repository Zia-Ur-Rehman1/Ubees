package com.example.ubees.model;

public class Products {

    String name;
    String desc;
    String status;
    String quantity;
    String price;

    String imgId;
    public Products(){}

    @Override
    public String toString() {
        return "Products{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", status='" + status + '\'' +
                ", quantity='" + quantity + '\'' +
                ", price='" + price + '\'' +
                ", imgId='" + imgId + '\'' +
                '}';
    }

    public Products(String name, String desc, String status, String quantity, String price, String imgId) {
        this.name = name;
        this.desc = desc;
        this.status = status;
        this.quantity = quantity;
        this.price = price;
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }
}
