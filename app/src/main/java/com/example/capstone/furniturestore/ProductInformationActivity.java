package com.example.capstone.furniturestore;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.furniturestore.Database.Database;
import com.example.capstone.furniturestore.Models.Product;
import com.example.capstone.furniturestore.Models.ProductInformation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductInformationActivity extends AppCompatActivity {

    private DatabaseReference productDatabase, prodInfoDatabase;
    Toolbar toolbar;
    TextView txtProductName, txtProductSalePrice, txtProductPrice, txtProductInfo;
    ImageView imgProduct;
    Button btnAddtoCart;
    String  ProductID = "", PageName ="";
    Product current_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_information);

        productDatabase =  FirebaseDatabase.getInstance().getReference("Products");
        prodInfoDatabase = FirebaseDatabase.getInstance().getReference("ProductInformation");
        Intent i = getIntent();
        ProductID = i.getExtras().getString("ProductID");
        PageName = i.getExtras().getString("PageName");


        //toolBar settings
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //    toolbar.setTitleTextColor(1);
        getSupportActionBar().setTitle(" Product Information");

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                onBackPressed(); // Implemented by activity
            }
        });


        imgProduct = (ImageView) findViewById(R.id.imgProduct);
        txtProductName = (TextView) findViewById(R.id.txt_productName);
        txtProductSalePrice = (TextView) findViewById(R.id.txt_productsalePrice);
        txtProductPrice = (TextView) findViewById(R.id.txt_productprice);
        txtProductInfo = (TextView) findViewById(R.id.txt_Information);

        btnAddtoCart = (Button) findViewById( R.id.btn_AddToCart);

        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                List<Product> tem=new ArrayList<>();
                tem.addAll(new Database(getApplicationContext()).getCarts());
                boolean isAlready=false;
                for(Product product:tem){
                    if(current_product.getProductID().equalsIgnoreCase(product.getProductID())){
                        isAlready=true;
                    }

                }
                Log.e("==value", String.valueOf(current_product.getProductPrice()));
                if(isAlready){
                    Toast.makeText(ProductInformationActivity.this,"product is already in cart",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    new Database(ProductInformationActivity.this).addToCart((current_product));
                    current_product.setProductImage(current_product.getProductImage());
                    current_product.setProductPrice(current_product.getProductPrice());

                    Toast.makeText(ProductInformationActivity.this,"Added",Toast.LENGTH_SHORT).show();
                }


            }
        });

        productDatabase.orderByChild("ProductID").equalTo(ProductID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer i = 0;
                for(DataSnapshot productSnapshot : dataSnapshot.getChildren())
                {
                    Product products = productSnapshot.getValue(Product.class);
                    String prod_id = products.getProductID();

                        current_product=products;
                        Picasso.with(getBaseContext()).load(products.getProductImage()).into(imgProduct);
                        txtProductName.setText(products.getProductName());
                        txtProductPrice.setText(String.valueOf(products.getProductPrice()));
                        txtProductPrice.setPaintFlags(txtProductPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        txtProductSalePrice.setText(String.valueOf(products.getProductSalePrice()));

                    i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        prodInfoDatabase.orderByChild("ProductID").equalTo(ProductID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer i = 0;
                for(DataSnapshot productSnapshot : dataSnapshot.getChildren())
                {
                    ProductInformation products = productSnapshot.getValue(ProductInformation.class);
                    String prod_id = products.getProductID();

                    if(PageName.equals("ProductInfo")) {
                        txtProductInfo.setText(Html.fromHtml(products.getProductInformation()));
                    }
                    else if (PageName.equals("Productspecification"))
                    {
                        txtProductInfo.setText(Html.fromHtml(products.getProductSpecification()));

                    }
                    else if (PageName.equals("ProductShipping"))
                    {
                        txtProductInfo.setText(Html.fromHtml(products.getProductShipping()));

                    }


                        i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
