package com.example.ubees;

public class Products {

    String name;
    String desc;
    int status;
    int quantity;
    int price;
    public Products(){}
    public Products(String name, String desc, int status, int quantity, int price) {
        this.name = name;
        this.desc = desc;
        this.status = status;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Products{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", status=" + status +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
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
