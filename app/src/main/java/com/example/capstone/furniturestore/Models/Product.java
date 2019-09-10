package com.example.capstone.furniturestore.Models;

/**
 * Created by tejalpatel on 2018-03-19.
 */

public class Product {

    private String ProductID;
    private String ProductName;
    private String ProductImage;
    private double ProductPrice;
    private String ProductPricenew;
    private double ProductSalePrice;
    private String ProductQunt="1";
    private String ProductBrand;
    private String ProductColour;
    private String ProductDept;
    private String ProductStyle;
    private String ProductManufacturer;
    private String ProductSale;
    private String ProductCatID;
    private String ProductSaleEndDate;


    public Product() {
    }

    public Product(String productID, String productName, String productImage, double productPrice, double productSalePrice)
    {
        this.setProductID(productID);
        this.setProductName(productName);
        this.setProductImage(productImage);
        this.setProductPrice(productPrice);



    }

    private int ProductSaleLimit;
    private String number;

    public Product(String productId, String productName, String productPrice, String productQuantity, String productSale, String productImage) {
        ProductID = productId;
        ProductName = productName;
        ProductPricenew = productPrice;
        ProductQunt = productQuantity;
        ProductSale = productSale;
        ProductImage =productImage;

    }

  public Product(String productID, String productName, String productImage, double productPrice, double productSalePrice, String productQunt, String productBrand, String productColour, String productDept, String productStyle, String productManufacturer, String productSale, String productCatID, int productSaleLimit, String productSaleEndDate) {
        ProductID = productID;
        ProductName = productName;
        ProductImage = productImage;
        ProductPrice = productPrice;
        ProductSalePrice = productSalePrice;
        ProductQunt = productQunt;
        ProductBrand = productBrand;
        ProductColour = productColour;
        ProductDept = productDept;
        ProductStyle = productStyle;
        ProductSaleLimit = productSaleLimit;
        ProductManufacturer = productManufacturer;
        ProductSale = productSale;
        ProductCatID = productCatID;
        ProductSaleEndDate = productSaleEndDate;
    }



    public String getProductSaleEndDate() {
        return ProductSaleEndDate;
    }

    public void setProductSaleEndDate(String productSaleEndDate) {
        ProductSaleEndDate = productSaleEndDate;
    }

    public int getProductSaleLimit() {
        return ProductSaleLimit;
    }

    public void setProductSaleLimit(int productSaleLimit) {
        ProductSaleLimit = productSaleLimit;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public double getProductPrice() {
        return ProductPrice;
    }

    public String getProductPricenew() {
        return ProductPricenew;
    }

    public void setProductPricenew(String productPricenew) {
        ProductPricenew = productPricenew;
    }

    public void setProductPrice(double productPrice) {
        ProductPrice = productPrice;
    }

    public double getProductSalePrice() {
        return ProductSalePrice;
    }

    public void setProductSalePrice(double productSalePrice) {
        ProductSalePrice = productSalePrice;
    }


    public String getProductQunt() {
        return ProductQunt;
    }

    public void setProductQunt(String productQunt) {
        ProductQunt = productQunt;
    }

    public String getProductBrand() {
        return ProductBrand;
    }

    public void setProductBrand(String productBrand) {
        ProductBrand = productBrand;
    }

    public String getProductColour() {
        return ProductColour;
    }

    public void setProductColour(String productColour) {
        ProductColour = productColour;
    }

    public String getProductDept() {
        return ProductDept;
    }

    public void setProductDept(String productDept) {
        ProductDept = productDept;
    }

    public String getProductStyle() {
        return ProductStyle;
    }

    public void setProductStyle(String productStyle) {
        ProductStyle = productStyle;
    }


    public String getProductManufacturer() {
        return ProductManufacturer;
    }

    public void setProductManufacturer(String productManufacturer) {
        ProductManufacturer = productManufacturer;
    }

    public String getProductSale() {
        return ProductSale;
    }

    public void setProductSale(String productSale) {
        ProductSale = productSale;
    }

    public String getProductCatID() {
        return ProductCatID;
    }

    public void setProductCatID(String productCatID) {
        ProductCatID = productCatID;
    }

    public String getnumber() {
        return number;
    }


}
