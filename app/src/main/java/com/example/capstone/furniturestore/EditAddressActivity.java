package com.example.capstone.furniturestore;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.furniturestore.Models.Address;
import com.example.capstone.furniturestore.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditAddressActivity extends AppCompatActivity {

    //toolbar
    Toolbar toolbar;

    String UID;
    String UserID, UserName;
    String name,address,city,state,postalcode,phonenumber;

    Intent intent;

    EditText edit_getusername,edit_getuseraddress,edit_getusercity,edit_getuserstate,edit_getuserpostalcode,edit_getuserphone;
    Button buttonUpdateAddress,buttonDeleteAddress;

    DatabaseReference EditaddressDatabase;
    DatabaseReference DeleteAddress;

    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "User";
    public static final String Userid = "UseridKey";
    public static final String Name = "UserNameKey";

    Map<String,Object> AddressUpdate = new HashMap<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);


        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        UserID = (sharedPreferences.getString(Userid, ""));
        UserName = (sharedPreferences.getString(Name, ""));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Address");

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed(); // Implemented by activity
            }
        });


        //database
        EditaddressDatabase = FirebaseDatabase.getInstance().getReference("User");
        DeleteAddress = FirebaseDatabase.getInstance().getReference("User");


        //
        edit_getusername = (EditText) findViewById(R.id.text_getuserfullname);
        edit_getuseraddress = (EditText) findViewById(R.id.text_getuserAddress);
        edit_getusercity =  (EditText) findViewById(R.id.text_getuserCity);
        edit_getuserstate = (EditText) findViewById(R.id.text_getuserState);
        edit_getuserpostalcode = (EditText) findViewById(R.id.text_getuserPostalcode);
        edit_getuserphone = (EditText) findViewById(R.id.text_getuserPhone);


        intent = getIntent();

        name =  intent.getExtras().getString("fullname");
        edit_getusername.setText(name);

        UID = intent.getExtras().getString("addressid");

        address = intent.getExtras().getString("useraddress");
        edit_getuseraddress.setText(address);

        city = intent.getExtras().getString("usercity");
        edit_getusercity.setText(city);

        state = intent.getExtras().getString("userstate");
        edit_getuserstate.setText(state);

        postalcode =  intent.getExtras().getString("userpostalcode");
        edit_getuserpostalcode.setText(postalcode);

        phonenumber = intent.getExtras().getString("userphonenumber");
        edit_getuserphone.setText(phonenumber);


        //Update Address
        buttonUpdateAddress = (Button)findViewById(R.id.button_updateaddress);
        buttonUpdateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddressUpdate.put("ufullname",edit_getusername.getText().toString());
                AddressUpdate.put("uaddress",edit_getuseraddress.getText().toString());
                AddressUpdate.put("ucity",edit_getusercity.getText().toString());
                AddressUpdate.put("ustate",edit_getuserstate.getText().toString());
                AddressUpdate.put("upostalcode",edit_getuserpostalcode.getText().toString());
                AddressUpdate.put("uphone",edit_getuserphone.getText().toString());

                EditaddressDatabase.child(UserID).child("address").child(UID).updateChildren(AddressUpdate);
                Toast.makeText(EditAddressActivity.this,"Data Updated",Toast.LENGTH_LONG ).show();

                /*edit_getusername.setText("");
                edit_getuseraddress.setText("");
                edit_getusercity.setText("");
                edit_getuserstate.setText("");
                edit_getuserpostalcode.setText("");
                edit_getuserphone.setText("");*/

                intent = new Intent(EditAddressActivity.this,AddAddressActivity.class);
                startActivity(intent);

                }

        });


        //Delete address
        buttonDeleteAddress = (Button)findViewById(R.id.button_deleteaddress);
        buttonDeleteAddress.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(EditAddressActivity.this);
                alertdialog.setTitle("Are you sure you want to Delete this Address ?");

                alertdialog.setPositiveButton("Delete", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which) {

                        EditaddressDatabase.child(UserID).child("address").child(UID).removeValue();

                    }
                });

                alertdialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });

                alertdialog.show();

            }
        });




    }
}
