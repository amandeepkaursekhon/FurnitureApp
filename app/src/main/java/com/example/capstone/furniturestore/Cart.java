package com.example.capstone.furniturestore;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.furniturestore.Database.Database;
import com.example.capstone.furniturestore.Helper.RecyclerItemTouchHelper;
import com.example.capstone.furniturestore.Interface.RecyclerItemTouchHelperListener;
import com.example.capstone.furniturestore.Models.Config;
import com.example.capstone.furniturestore.Models.Product;
import com.example.capstone.furniturestore.Models.Request;
import com.example.capstone.furniturestore.ViewHolder.CartAdapter;
import com.example.capstone.furniturestore.ViewHolder.CartViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity implements RecyclerItemTouchHelperListener {
    private static final String TAG = "data";
    private static final int PAYPAL_REQUEST_CODE = 7171;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RelativeLayout rootLayout;

    FirebaseDatabase database;
    DatabaseReference requests;

    public TextView txtTotalPrice;
    TextView txtname;
    Button btnplace;
    TextView city;
    Number nbr;
    TextView province;

    //shared preference
    SharedPreferences sharedPreferences, sharedPref;
    public static final String MyPREFERENCES = "User" ;
    public static final String CartPREFERENCES = "Cart" ;
    public static final String count = "count";
    public static final String Name = "UserNameKey";
    public static final String Userid = "UseridKey";
    private String  ProductID = "", UserID="";
    private  int cartCount= 0;

    Toolbar toolbar;
    List<Product> cart = new ArrayList<>();
    CartAdapter adapter;
    //Paypal payment

    static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);
    String name, phone, address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //initpaypal
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        //Shared preference
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        UserID = (sharedPreferences.getString(Userid, ""));
        sharedPref = getSharedPreferences(CartPREFERENCES, Context.MODE_PRIVATE);
        cartCount =  (sharedPref.getInt(count, 0));


        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        //toolBar settings
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" My Cart");

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

        recyclerView = (RecyclerView) findViewById(R.id.listCart);
        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);

        //Swipe to delete

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);


        txtTotalPrice = (TextView) findViewById(R.id.total);
        btnplace = (Button) findViewById(R.id.btn_placeorder);
        btnplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Product> orders = new Database(getApplicationContext()).getCarts();
                if (orders.size() == 0) {

                    View view = findViewById(android.R.id.content);
                    Snackbar.make(view, "First add item in cart..", Snackbar.LENGTH_LONG).show();


                } else {
                    showAlertDialog();
                }
            }
        });


        loadListOrder();
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("One more step!");
        alertDialog.setMessage("Enter your Information:");

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(lp);

        linearLayout.setOrientation(LinearLayout.VERTICAL);

        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;

        final EditText edtname = new EditText(Cart.this);
        final EditText edtphone = new EditText(Cart.this);
        edtphone.setHorizontallyScrolling(true);
        edtphone.setInputType(EditorInfo.TYPE_NUMBER_FLAG_SIGNED|EditorInfo.TYPE_CLASS_NUMBER);
        final EditText edtAddress = new EditText(Cart.this);


        edtname.setLayoutParams(lp);
        edtphone.setLayoutParams(lp);
        edtAddress.setLayoutParams(lp);


        edtname.setHint("Enter Name");
        edtphone.setHint("Enter Phone");
        edtAddress.setHint("Enter Address");

        linearLayout.addView(edtname);
        linearLayout.addView(edtphone);
        linearLayout.addView(edtAddress);

        alertDialog.setView(linearLayout);// add edit text to alert dialog


        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (edtphone.getText().toString().equals("") || edtAddress.getText().toString().equals("") || edtname.getText().toString().equals("")) {

                    View view = findViewById(android.R.id.content);
                    Snackbar.make(view, "Fill information first..", Snackbar.LENGTH_LONG).show();

                } else {

                    name = edtname.getText().toString();
                    phone = edtphone.getText().toString();
                    address = edtAddress.getText().toString();

                    String formatAmount = txtTotalPrice.getText().toString()
                            .replace("$", "")
                            .replace(",", "");


                    PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(formatAmount),
                            "CAD",
                            "Furniture app Order",
                            PayPalPayment.PAYMENT_INTENT_SALE);
                    Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                    intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
                    intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
                    startActivityForResult(intent, PAYPAL_REQUEST_CODE);

                }

        }
    });

    //Show Paypal to payment

        //first , get Address and Comment from Alert Dialog

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                alertDialog.show();
            }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PAYPAL_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation != null)
                {
                    try{
                        String paymentDetail = confirmation.toJSONObject().toString(4);
                        JSONObject jsonObject = new JSONObject(paymentDetail);



                        //Create new Request
                        Request request = new Request(
                                name,
                                phone,
                                address,
                                jsonObject.getJSONObject("response").getString("state"), // State from JSON
                                "0", // status
                                txtTotalPrice.getText().toString(),
                                cart
                        );

                        //Submit to Firebase
                        //We will using System.CurrentMilli to key
                        requests.child(String.valueOf(System.currentTimeMillis()))
                                .setValue(request);
                        //delete cart
                        new Database(getBaseContext()).cleanCart();
                        if(cartCount > 0) {
                            cartCount = cartCount - 1;
                        }
                        else {
                            cartCount = 0;
                        }

                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt(count , cartCount);
                        editor.apply();

                        Toast.makeText(Cart.this,"Thank you , order placed", Toast.LENGTH_SHORT).show();

                        finish();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if(resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this, "Payment cancel", Toast.LENGTH_SHORT).show();
            else if(resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
                Toast.makeText(this, "Invalid payment", Toast.LENGTH_SHORT).show();
        }

    }

    private void loadListOrder() {
        cart.clear();
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart, this);
        recyclerView.setAdapter(adapter);


        float total = 0;

        for(Product order:cart) {
             total+=(Float.parseFloat(String.valueOf(order.getProductPricenew())));

            Log.e("==value", String.valueOf(order.getProductPricenew()));
            Log.e("===2nd value", order.getProductName());
            txtTotalPrice.setText(total+"");
        }
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if(viewHolder instanceof CartViewHolder)
        {
            String name = ((CartAdapter)recyclerView.getAdapter()).getItem(viewHolder.getAdapterPosition()).getProductName();
            final Product  deleteItem = ((CartAdapter)recyclerView.getAdapter()).getItem(viewHolder.getAdapterPosition());

            final int deleteIndex = viewHolder.getAdapterPosition();

            adapter.removeItem(deleteIndex);
            new Database(getBaseContext()).removeFromCart(deleteItem.getProductID());


            if(cartCount > 0) {
                cartCount = cartCount - 1;
            }
            else {
                cartCount = 0;
            }

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(count , cartCount);
            editor.apply();

            float total = 0;
            //make snackbar
            Snackbar snackbar = Snackbar.make(rootLayout,name + "removed from cart !" ,Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            new Database(getBaseContext()).addToCart(deleteItem);


            Log.e("price22", String.valueOf(deleteItem.getProductPricenew()));
            deleteItem.setProductPrice(Double.parseDouble(deleteItem.getProductPricenew()));
            Log.e("price", String.valueOf(deleteItem.getProductPrice()));
            adapter.restoreItem(deleteItem,deleteIndex);
            }
});
snackbar.setActionTextColor(Color.YELLOW);
snackbar.show();
        }
    }
}

