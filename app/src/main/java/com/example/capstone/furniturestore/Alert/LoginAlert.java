package com.example.capstone.furniturestore.Alert;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.example.capstone.furniturestore.LoginActivity;
import com.example.capstone.furniturestore.ProductDetailActivity;

/**
 * Created by tejalpatel on 2018-04-24.
 */

public class LoginAlert {

    public LoginAlert(final Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("LogIn First");

        // set dialog message
        alertDialogBuilder
                .setMessage("LogIn First to see your Account!")
                .setCancelable(true)

                .setPositiveButton("LogIn", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        Intent i = new Intent(context,LoginActivity.class);
                        context.startActivity(i);
                    }
                })
                .setNegativeButton("Cancel", null);;



        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
