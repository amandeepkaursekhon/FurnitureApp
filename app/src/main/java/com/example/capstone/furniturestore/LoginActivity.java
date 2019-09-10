package com.example.capstone.furniturestore;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.furniturestore.CurrentUser.CurrentUser;
import com.example.capstone.furniturestore.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {

    //Database reference
    private DatabaseReference mDatabase;

    //SharedPreferences
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "User" ;
    public static final String Name = "UserNameKey";
    public static final String Userid = "UseridKey";

    Toolbar toolbar;
    private EditText editText_username, editText_password;
    private Button btn_login;
    private TextView txtlink_SignUp;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mDatabase = FirebaseDatabase.getInstance().getReference("User");
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        //toolBar settings
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle(" LogIn");

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


        editText_username = (EditText)  findViewById(R.id.editTxt_UserName);

        editText_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String username = editText_username.getText().toString();
                    if(username.length() < 10){
                        editText_username.setError("atleast 10 digit for Phone number!");
                    }
                }
            }
        });

        editText_password = (EditText) findViewById(R.id.editTxt_Password);
        btn_login = (Button) findViewById(R.id.btnLogin);
        txtlink_SignUp = (TextView) findViewById(R.id.txtlinkSignUp);

        txtlink_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    intent = new Intent(LoginActivity.this, SignUpActivity.class);
                    startActivity(intent);


            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editText_username.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this," UserName is required",Toast.LENGTH_LONG).show();;

                }
                else if (editText_password.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this," Password is required",Toast.LENGTH_LONG).show();;

                }
                else {
                    getUser();
                }
            }
        });
    }
    private void getUser()
    {

        final String userName = editText_username.getText().toString();
        final String passWord = editText_password.getText().toString();

        mDatabase.orderByChild("userId").equalTo(userName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {

                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        if (userSnapshot.hasChildren()) {

                            User user = userSnapshot.getValue(User.class);

                            if (passWord.equals(user.getPassword())) {

                                editor.putString(Userid, user.getUserId());
                                editor.putString(Name, user.getUserName());
                                editor.apply();

                              Intent  intent = new Intent(LoginActivity.this, StoreActivity.class);
                                CurrentUser.currentUser = user;
                                startActivity(intent);
                            } else {
                                ShowAlert("Wrong Password");
                            }
                        }
                    }
                }
                else {

                    ShowAlert("User is not Exist");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public void ShowAlert(String msg){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                LoginActivity.this);

        // set dialog message
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(false)

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
