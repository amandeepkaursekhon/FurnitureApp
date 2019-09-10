package com.example.capstone.furniturestore.Models;

import java.util.List;

/**
 * Created by amandeepsekhon on 2018-03-28.
 */

public class Request {
    private String name;
    private String phone;
    private String address;
    private String paymentState;
    private String status;
    private String total;
    private List<Product> products;

    public Request(String name, String phone, String address, String paymentState, String status, String total, List<Product> products) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.paymentState = paymentState;
        this.status = "0";  //default is 0  , 0 :Placed , 1:Shipping , 2 :Shipped
        this.total = total;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Request() {
    }
}
