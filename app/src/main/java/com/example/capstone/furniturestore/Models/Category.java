package com.example.capstone.furniturestore.Models;

/**
 * Created by tejalpatel on 2018-03-18.
 */

public class Category {

    private String CategoryImage;
    private String CategoryName;
    private String CategoryID;
    private String DepartmentID;

    public Category() {
    }

    public Category(String categoryImage, String categoryName, String categoryID, String departmentID) {
        CategoryImage = categoryImage;
        CategoryName = categoryName;
        CategoryID = categoryID;
        DepartmentID = departmentID;
    }

    public String getCategoryImage() {
        return CategoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        CategoryImage = categoryImage;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(String departmentID) {
        DepartmentID = departmentID;
    }
}
