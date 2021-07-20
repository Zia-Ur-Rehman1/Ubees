package com.example.ubees.model;

public class Cart {
    String ImgId;
    String product_name;
    String product_quantity;
    String product_price;

    public Cart(){}
    public Cart(String ImgId,String product_name, String product_quantity, String product_price) {
        this.product_name = product_name;
        this.product_quantity = product_quantity;
        this.product_price = product_price;
        this.ImgId = ImgId;
    }
    public String getImgId() {
        return ImgId;
    }

    public void setImgId(String imgId) {
        ImgId = imgId;
    }
    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }
}
