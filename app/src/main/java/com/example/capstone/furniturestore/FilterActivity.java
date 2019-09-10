package com.example.capstone.furniturestore;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.furniturestore.Adapter.SearchListAdapter;
import com.example.capstone.furniturestore.Models.Category;
import com.example.capstone.furniturestore.Models.Filter_Child;
import com.example.capstone.furniturestore.Models.Filter_Parent;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.HashMap;

public class FilterActivity extends AppCompatActivity {

    private RecyclerView Filter_RecyclerView;
    private Context mContext;
    ArrayList<String> selecteditem = new ArrayList<>();
    HashMap<String, String> filterItem = new HashMap<>();
    Toolbar toolbar;
    Button btn_apply, btn_clear;
    String CategoryID="",CategoryName="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        //toolBar settings
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle(" Filter");

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


        mContext = FilterActivity.this;
        Filter_RecyclerView = findViewById(R.id.recycler_Filter);
        RecyclerDataAdapter recyclerDataAdapter = new RecyclerDataAdapter(getDummyDataToPass());
        Filter_RecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        Filter_RecyclerView.setAdapter(recyclerDataAdapter);
        Filter_RecyclerView.setHasFixedSize(true);

        if(getIntent() != null) {
            Intent i = getIntent();

            if (getIntent().hasExtra("CategoryID")) {
                CategoryID = i.getExtras().getString("CategoryID");
                CategoryName   = i.getExtras().getString("CategoryName");

            }
        }

        btn_apply = (Button) findViewById(R.id.btn_apply);
        btn_clear = (Button) findViewById(R.id.btn_clear);

        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilterActivity.this,ProductListActivity.class);

                intent.putExtra("selectedItem", filterItem);
                intent.putExtra("CategoryID", CategoryID );
                intent.putExtra("CategoryName", CategoryName);

                startActivity(intent);
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecteditem.clear();
                RecyclerDataAdapter recyclerDataAdapter = new RecyclerDataAdapter(getDummyDataToPass());
                Filter_RecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                Filter_RecyclerView.setAdapter(recyclerDataAdapter);
                Filter_RecyclerView.setHasFixedSize(true);

            }
        });

    }

    private ArrayList<Filter_Parent> getDummyDataToPass() {
        ArrayList<Filter_Parent> DataItems = new ArrayList<>();
        ArrayList<Filter_Child> ChildDataItems;
        Filter_Parent parent_dataItem;
        Filter_Child child_dataItem;

        /////////
        parent_dataItem = new Filter_Parent();
        parent_dataItem.setParentName("Color");
        ChildDataItems = new ArrayList<>();
        //
        child_dataItem = new Filter_Child();
        child_dataItem.setChildName("Black");
        ChildDataItems.add(child_dataItem);
        //
        child_dataItem = new Filter_Child();
        child_dataItem.setChildName("White");
        ChildDataItems.add(child_dataItem);

        //
        child_dataItem = new Filter_Child();
        child_dataItem.setChildName("Brown");
        ChildDataItems.add(child_dataItem);

        //
        child_dataItem = new Filter_Child();
        child_dataItem.setChildName("Red");
        ChildDataItems.add(child_dataItem);

        //
        child_dataItem = new Filter_Child();
        child_dataItem.setChildName("Orange");
        ChildDataItems.add(child_dataItem);

        //
        child_dataItem = new Filter_Child();
        child_dataItem.setChildName("Green");
        ChildDataItems.add(child_dataItem);

        //
        child_dataItem = new Filter_Child();
        child_dataItem.setChildName("Blue");
        ChildDataItems.add(child_dataItem);

        //
        parent_dataItem.setChildDataItems(ChildDataItems);
        DataItems.add(parent_dataItem);



        ////////
        parent_dataItem = new Filter_Parent();
        parent_dataItem.setParentName("Availability");
        ChildDataItems = new ArrayList<>();
        //
        child_dataItem = new Filter_Child();
        child_dataItem.setChildName("In Stock Only");
        ChildDataItems.add(child_dataItem);
        //

        parent_dataItem.setChildDataItems(ChildDataItems);
        DataItems.add(parent_dataItem);



        ////////
        parent_dataItem = new Filter_Parent();
        parent_dataItem.setParentName("Price Per Item");
        ChildDataItems = new ArrayList<>();
        //
        child_dataItem = new Filter_Child();
        child_dataItem.setChildName("Under $500");
        ChildDataItems.add(child_dataItem);
        //
        child_dataItem = new Filter_Child();
        child_dataItem.setChildName("$500 to $1000");
        ChildDataItems.add(child_dataItem);
        //
        child_dataItem = new Filter_Child();
        child_dataItem.setChildName("1000 to $2000");
        ChildDataItems.add(child_dataItem);
        //
        child_dataItem = new Filter_Child();
        child_dataItem.setChildName("$2000 to $3000");
        ChildDataItems.add(child_dataItem);
        //
        child_dataItem = new Filter_Child();
        child_dataItem.setChildName("$3000 and above");
        ChildDataItems.add(child_dataItem);
        //
        parent_dataItem.setChildDataItems(ChildDataItems);
        DataItems.add(parent_dataItem);

        ////////
        parent_dataItem = new Filter_Parent();
        parent_dataItem.setParentName("Special Offer");
        ChildDataItems = new ArrayList<>();
        //
        child_dataItem = new Filter_Child();
        child_dataItem.setChildName("New arrival");
        ChildDataItems.add(child_dataItem);
        //
        child_dataItem = new Filter_Child();
        child_dataItem.setChildName("Sale");
        ChildDataItems.add(child_dataItem);
        //
        child_dataItem = new Filter_Child();
        child_dataItem.setChildName("Closeout");
        ChildDataItems.add(child_dataItem);
        //
        parent_dataItem.setChildDataItems(ChildDataItems);
        DataItems.add(parent_dataItem);
        ////////
        return DataItems;
    }






    private class RecyclerDataAdapter extends RecyclerView.Adapter<RecyclerDataAdapter.MyViewHolder> {
        private ArrayList<Filter_Parent> parent_dataItems;

        RecyclerDataAdapter(ArrayList<Filter_Parent> parent_dataItems) {
            this.parent_dataItems = parent_dataItems;
        }

        @Override
        public RecyclerDataAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_item_list, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(RecyclerDataAdapter.MyViewHolder holder, int position) {
            Filter_Parent ParentDataItem = parent_dataItems.get(position);
            holder.textView_parentName.setText(ParentDataItem.getParentName());
            //
            int noOfChildTextViews = holder.linearLayout_childItems.getChildCount();
            int noOfChild = ParentDataItem.getChildDataItems().size();
            if (noOfChild < noOfChildTextViews) {
                for (int index = noOfChild; index < noOfChildTextViews; index++) {
                    TextView currentTextView = (TextView) holder.linearLayout_childItems.getChildAt(index);
                    currentTextView.setVisibility(View.GONE);
                }
            }
            for (int textViewIndex = 0; textViewIndex < noOfChild; textViewIndex++) {
                TextView currentTextView = (TextView) holder.linearLayout_childItems.getChildAt(textViewIndex);
                currentTextView.setText(ParentDataItem.getChildDataItems().get(textViewIndex).getChildName());
                /*currentTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "" + ((TextView) view).getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });*/
            }
        }

        @Override
        public int getItemCount() {
            return parent_dataItems.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private Context context;
            private TextView textView_parentName;
            private LinearLayout linearLayout_childItems;

            MyViewHolder(View itemView) {
                super(itemView);
                context = itemView.getContext();
                textView_parentName = itemView.findViewById(R.id.tv_parentName);
                linearLayout_childItems = itemView.findViewById(R.id.ll_child_items);
                linearLayout_childItems.setVisibility(View.GONE);
                int intMaxNoOfChild = 0;
                for (int index = 0; index < parent_dataItems.size(); index++) {
                    int intMaxSizeTemp = parent_dataItems.get(index).getChildDataItems().size();
                    if (intMaxSizeTemp > intMaxNoOfChild) intMaxNoOfChild = intMaxSizeTemp;
                }
                for (int indexView = 0; indexView < intMaxNoOfChild; indexView++) {
                    TextView textView = new TextView(context);
                    textView.setId(indexView);
                    textView.setPadding(20, 20, 0, 20);
                    textView.setGravity(Gravity.LEFT);
                    textView.setTextColor(Color.BLACK);
                    textView.setBackground(ContextCompat.getDrawable(context, R.drawable.background_sub_module_text));
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    textView.setOnClickListener(this);
                    linearLayout_childItems.addView(textView, layoutParams);
                }
                textView_parentName.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {



                if (view.getId() == R.id.tv_parentName) {
                    if (linearLayout_childItems.getVisibility() == View.VISIBLE) {
                        linearLayout_childItems.setVisibility(View.GONE);
                    } else {
                        linearLayout_childItems.setVisibility(View.VISIBLE);
                    }
                } else {
                    TextView textViewClicked = (TextView) view;


                    if(textViewClicked.getCurrentTextColor() == Color.MAGENTA){
                        textViewClicked.setTextColor(Color.BLACK);
                          selecteditem.remove(textViewClicked.getText());
                        filterItem.values().remove(textViewClicked.getText());

                    }
                    else if(textViewClicked.getCurrentTextColor() == Color.BLACK ) {
                        textViewClicked.setTextColor(Color.MAGENTA);
                        selecteditem.add(textViewClicked.getText().toString());
                        filterItem.put(textView_parentName.getText().toString(),textViewClicked.getText().toString());
                    }
                }
            }
        }
    }
}

