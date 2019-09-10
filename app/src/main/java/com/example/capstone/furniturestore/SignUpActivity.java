package com.example.capstone.furniturestore;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstone.furniturestore.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    //Database reference
    private DatabaseReference mDatabase;
    private EditText editText_userID, editText_password,editText_Confirmpassword , editText_userFullname;
    private Button btn_register;
    private Intent intent;
    private Boolean ExistUser =  false;
    Toolbar toolbar;
    String ID,Username, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mDatabase = FirebaseDatabase.getInstance().getReference("User");

        //toolBar settings
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle(" SignUp");

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

        editText_userID = (EditText)  findViewById(R.id.editTxt_UserName);
        editText_userFullname = (EditText)  findViewById(R.id.editTxt_Fullname);
        editText_userID.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String username = editText_userID.getText().toString();
                    if(username.length() < 10){
                        editText_userID.setError("atleast 10 digit for Phone number!");
                    }
                }
            }
        });


        editText_password = (EditText) findViewById(R.id.editTxt_Password);
        editText_Confirmpassword = (EditText) findViewById(R.id.editTxt_ConfirmPassword);

        btn_register = (Button) findViewById(R.id.btnRegister);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ID = editText_userID.getText().toString() ;
                Username = editText_userFullname.getText().toString();
                Password = editText_password.getText().toString();

                mDatabase.orderByChild("userId").equalTo(ID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            ExistUser = true;
                            Toast.makeText(SignUpActivity.this," UserName already Exists",Toast.LENGTH_LONG).show();;

                        }
                        else {

                            if(editText_password.getText().toString().equals(editText_Confirmpassword.getText().toString())){
                                //Add user in database
                                addUser();
                            }
                            else {
                                Toast.makeText(SignUpActivity.this," Password dosen't match",Toast.LENGTH_LONG).show();;

                            }

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        }
        });

        }
        private void addUser()
        {

            if(!TextUtils.isEmpty(ID)) {

                if(ExistUser == false) {
                    User user = new User(ID, Username, Password, null);

                    mDatabase.child(ID).setValue(user);
                    intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this," UserName already Exists",Toast.LENGTH_LONG).show();;

                }

            }else {
                Toast.makeText(this,"You Should enter UserName",Toast.LENGTH_LONG).show();;
            }
        }
    }
