package com.example.capstone.furniturestore.Models;

/**
 * Created by tejalpatel on 2018-03-22.
 */

public class Favourite {
    private String User_ID;
    private String Product_ID;
    private String Favorite_ID;
    private String Favorite_title;


    public Favourite() {
    }

    public Favourite( String product_ID, String favorite_ID, String favorite_title ) {
       // User_ID = user_ID;
        Product_ID = product_ID;
        Favorite_ID = favorite_ID;
        Favorite_title = favorite_title;

    }

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public String getProduct_ID() {
        return Product_ID;
    }

    public void setProduct_ID(String product_ID) {
        Product_ID = product_ID;
    }

    public String getFavorite_ID() {
        return Favorite_ID;
    }

    public void setFavorite_ID(String favorite_ID) {
        Favorite_ID = favorite_ID;
    }

    public String getFavorite_title() {
        return Favorite_title;
    }

    public void setFavorite_title(String favorite_title) {
        Favorite_title = favorite_title;
    }


}
