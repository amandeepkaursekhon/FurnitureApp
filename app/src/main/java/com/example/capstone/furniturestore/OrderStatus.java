package com.example.capstone.furniturestore;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.furniturestore.CurrentUser.CurrentUser;
import com.example.capstone.furniturestore.Models.Product;
import com.example.capstone.furniturestore.Models.Request;
import com.example.capstone.furniturestore.Models.User;
import com.example.capstone.furniturestore.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference requests;
    //Toolbar
    Toolbar toolbar;

    //Shared Preferences
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "User";
    public static final String Name = "UserNameKey";
    public static final String userid = "UseridKey";
    String UserID, UserName;
    int counter = 0;
    TextView txtcountitem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        //firebase
        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");

        //toolBar settings
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" My Orders");

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


        //Shared Preferences
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        UserName = (sharedPreferences.getString(Name, ""));
        UserID = (sharedPreferences.getString(userid, ""));


        recyclerView= (RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        txtcountitem = (TextView) findViewById(R.id.txt_vieworder);
        recyclerView.setLayoutManager(layoutManager);
        if(!UserID.isEmpty() && UserID != null) {
            loadOrders(CurrentUser.currentUser.getUserId());
        }
        else
        {
            txtcountitem.setText(counter + " Item");
            // Toast.makeText(OrderStatus.this,"Firstly sigun up for place order",Toast.LENGTH_SHORT).show();
        }

    }

    private void loadOrders(String userId) {
        Integer i = 0;

        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests.orderByChild("phone").equalTo(userId)


        ) {



            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {
                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtOrderDate.setText(CurrentUser.getDate(Long.parseLong(adapter.getRef(position).getKey())));
                viewHolder.txtOrderStatus.setText(convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderAddress.setText(model.getAddress());
                viewHolder.txtOrderPhone.setText(model.getPhone());
                viewHolder.txtOrderName.setText(model.getName());
                counter++;
                txtcountitem.setText(counter + " Item");


            }
        };
        recyclerView.setAdapter(adapter);
        i++;
    }

    private String convertCodeToStatus(String status) {
        if (status.equals("0"))
            return "placed";
        else if (status.equals("1"))
            return "On my way";
        else
            return "Shipped";
    }
}

 


