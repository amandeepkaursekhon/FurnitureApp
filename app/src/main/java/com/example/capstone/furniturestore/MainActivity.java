package com.example.capstone.furniturestore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button butnLogin, btnSignup;
    private TextView txtlinkGuest,txtslogun;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        butnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btnSignUp);
        txtlinkGuest = (TextView) findViewById(R.id.txtlinkGuest);

        txtslogun = (TextView)  findViewById(R.id.txtSlogan);
        txtslogun.setText("Furnish your home on your fingure tip");

        butnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        txtlinkGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(MainActivity.this,StoreActivity.class);
                startActivity(intent);
            }
        });
    }




}
