package com.example.capstone.furniturestore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.furniturestore.Adapter.EditFavouriteAdapter;
import com.example.capstone.furniturestore.Adapter.FavouriteAdapter;
import com.example.capstone.furniturestore.Models.Favourite;
import com.example.capstone.furniturestore.Models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditFavouriteActivity extends AppCompatActivity {

    //Database
    private DatabaseReference favouriteDatabase, productDatabase;

    //Toolbar
    Toolbar toolbar;

    //Recycler
    public RecyclerView favourite_RecyclerView;
    EditFavouriteAdapter fav_adapter;
    LinearLayoutManager layoutManager;
    Context context;
    final List<String> listProductID = new ArrayList<String>();
    ArrayList<Product> productList = new ArrayList<Product>();
    final List<String> selectedProductID = new ArrayList<String>();
    ArrayList<Favourite>favouriteList = new ArrayList<>();

    //Shared Preferences
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "User";
    public static final String Name = "UserNameKey";
    public static final String Userid = "UseridKey";
    String UserID, UserName;
    Button btnCancel,btndelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_favourite);


        //Shared Preferences
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        UserName = (sharedPreferences.getString(Name, ""));
        UserID = (sharedPreferences.getString(Userid, ""));


        //Database
        favouriteDatabase = FirebaseDatabase.getInstance().getReference("Favourites").child(UserID);
        productDatabase = FirebaseDatabase.getInstance().getReference("Products");


        btnCancel = (Button) findViewById(R.id.btn_Cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditFavouriteActivity.this,FavouriteActivity.class);
                startActivity(intent);
            }
        });

        btndelete = (Button) findViewById(R.id.btn_Deletefavourite);
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ts ="";
                for (String pid: selectedProductID) {
                    favouriteDatabase.child(pid).removeValue();
                }

            }
        });
        if(!UserID.isEmpty() && UserID != null)
        {
            //Display Favourite Items
            load_Favourite();
        }
        else {

        }



    }




    public  void load_Favourite(){
          favouriteDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer i = 0;
                favouriteList.clear();
                productList.clear();

                for(DataSnapshot favouriteSnapshot : dataSnapshot.getChildren())
                {
                    Favourite fav_product = favouriteSnapshot.getValue(Favourite.class);
                    String id = fav_product.getProduct_ID();

                    listProductID.add(fav_product.getProduct_ID());
                    favouriteList.add(fav_product);

                    productDatabase.orderByChild("ProductID").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                          //  productList.clear();
                            for(DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                                Product product = productSnapshot.getValue(Product.class);
                                productList.add(product);
                            }


                            fav_adapter = new EditFavouriteAdapter(productList,EditFavouriteActivity.this, selectedProductID, favouriteList) ;
                            favourite_RecyclerView = (RecyclerView) findViewById(R.id.recycle_EditFavourite);
                            favourite_RecyclerView.setHasFixedSize(true);
                            favourite_RecyclerView.setNestedScrollingEnabled(false);
                            layoutManager = new LinearLayoutManager(getBaseContext());
                            favourite_RecyclerView.setLayoutManager(new GridLayoutManager(EditFavouriteActivity.this, 2));
                            favourite_RecyclerView.setAdapter(fav_adapter);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
