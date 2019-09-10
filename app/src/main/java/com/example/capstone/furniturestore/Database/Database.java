package com.example.capstone.furniturestore.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.capstone.furniturestore.Models.Product;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amandeepsekhon on 2018-03-27.
 */

public class Database extends SQLiteAssetHelper{
    private static final String DB_NAME = "furnsqll.db";
    private static final int DB_VER = 2;
    public Database(Context context){
        super(context, DB_NAME,null,DB_VER);
    }
    public List<Product> getCarts()
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb =new SQLiteQueryBuilder();

        String[] sqlSelect={"ProductID","ProductName","ProductPrice","ProductQunt","ProductOffer","ProductImage"};
        String sqlTable = "OrderDetail";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

        final List<Product> result = new ArrayList<>();
        if (c.moveToFirst())
        {
            do{
                result.add(new Product(c.getString(c.getColumnIndex("ProductID")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("ProductPrice")),
                        c.getString(c.getColumnIndex("ProductQunt")),
                        c.getString(c.getColumnIndex("ProductOffer")),
                        c.getString(c.getColumnIndex("ProductImage"))
                ));
            } while (c.moveToNext());
        }
        return result;
    }

    public void addToCart(Product order )
    {

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail(ProductID,ProductName,ProductPrice,ProductQunt,ProductOffer,ProductImage)VALUES('%s' , '%s' ,  '%s' ,  '%s' , '%s', '%s');",
                order.getProductID() ,
                order.getProductName(),
                order.getProductPrice(),
                order.getProductQunt(),
                order.getProductSale(),
        order.getProductImage());
        db.execSQL(query);

    }

    public void cleanCart()
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }
    public int getCountCart() {
        int count=0;

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT COUNT(*) FROM OrderDetail");
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst())
        {
            do{
                count = cursor.getInt(0);
            }while (cursor.moveToNext());
        }
        return count;
    }


    public void updateCart(Product order) {

        SQLiteDatabase db = getReadableDatabase();
        String quant=order.getProductQunt();
        String query = "UPDATE OrderDetail SET ProductQunt = '"+ quant+"' WHERE ProductID = '"+ order.getProductID()+"'";
   db.execSQL(query);
    }


    public void removeFromCart(String productID) {
        SQLiteDatabase db = getReadableDatabase();
        String query =String.format("DELETE FROM OrderDetail WHERE ProductID = '%s' ", productID);
        db.execSQL(query);
    }
}



