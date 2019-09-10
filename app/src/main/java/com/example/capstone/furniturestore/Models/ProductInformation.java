package com.example.capstone.furniturestore.Models;

/**
 * Created by tejalpatel on 2018-04-09.
 */

public class ProductInformation {

    private String ProductID;
    private String ProductInformation;
    private String ProductSpecification;
    private String ProductShipping;
    public ProductInformation(){

    }

    public ProductInformation(String productID, String productInformation, String productSpecification, String productShipping) {
        ProductID = productID;
        ProductInformation = productInformation;
        ProductSpecification = productSpecification;
        ProductShipping = productShipping;
    }

    public String getProductShipping() {
        return ProductShipping;
    }

    public void setProductShipping(String productShipping) {
        ProductShipping = productShipping;
    }

    public String getProductID() {
        return ProductID;
    }

    public String getProductSpecification() {
        return ProductSpecification;
    }

    public void setProductSpecification(String productSpecification) {
        ProductSpecification = productSpecification;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getProductInformation() {
        return ProductInformation;
    }

    public void setProductInformation(String productInformation) {
        ProductInformation = productInformation;
    }
}
