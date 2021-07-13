package com.example.ubees;

public class Products {

    String name;
    String desc;
    int status;
    int quantity;
    int price;

    String imgId;
    public Products(){}
    public Products(String name, String desc, int status, int quantity, int price,String imgId) {
        this.name = name;
        this.desc = desc;
        this.status = status;
        this.quantity = quantity;
        this.price = price;
        this.imgId = imgId;
    }

    @Override
    public String toString() {
        return "Products{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", status=" + status +
                ", quantity=" + quantity +
                ", price=" + price +
                ", imgId='" + imgId + '\'' +
                '}';
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
