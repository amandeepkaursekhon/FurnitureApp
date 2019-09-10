package com.example.capstone.furniturestore.Models;

import java.util.HashMap;
import java.util.List;

/**
 * Created by tejalpatel on 2018-03-06.
 */

public class User {
    private String userId;
    private String userName;
    private String password;
    //private Object address;
    private HashMap<String,Object> address;

    public User(){

    }

    public User(String userId, String userName, String password,HashMap address) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }
    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }

    public HashMap<String,Object> getAddress(){
        return address;
    }


}
