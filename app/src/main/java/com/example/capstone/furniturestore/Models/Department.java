package com.example.capstone.furniturestore.Models;

/**
 * Created by tejalpatel on 2018-03-16.
 */

public class Department {

   private String DepartmentImage;
   private String DepartmentName;
   private String DepartmentID;

    public Department() {

    }
    public Department(String departmentImage, String departmentName) {
        DepartmentImage = departmentImage;
        DepartmentName = departmentName;
       // DepartmentID = departmentID;
    }

    public String getDepartmentImage() {
        return DepartmentImage;
    }

    public void setDepartmentImage(String departmentImage) {
        DepartmentImage = departmentImage;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public String getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(String departmentID) {
        DepartmentID = departmentID;
    }
}
