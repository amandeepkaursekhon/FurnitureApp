package com.example.capstone.furniturestore;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.provider.SearchRecentSuggestions;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.andremion.counterfab.CounterFab;
import com.example.capstone.furniturestore.Adapter.SearchListAdapter;
import com.example.capstone.furniturestore.Adapter.ViewPagerAdapter;
import com.example.capstone.furniturestore.Alert.LoginAlert;
import com.example.capstone.furniturestore.Database.Database;
import com.example.capstone.furniturestore.Helper.BadgeDrawable;
import com.example.capstone.furniturestore.Models.Category;
import com.example.capstone.furniturestore.Models.Department;
import com.example.capstone.furniturestore.Models.Product;
import com.example.capstone.furniturestore.ViewHolder.BottomNavigationViewHolder;
import com.example.capstone.furniturestore.ViewHolder.DepartmentViewHolder;
import com.example.capstone.furniturestore.ViewHolder.ProductViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.squareup.picasso.Picasso;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class StoreActivity extends AppCompatActivity {

    //Database reference
    private FirebaseDatabase database;
    private DatabaseReference deptDatabase,saleDatabase,categoryDatabase;
    //View pager
    ViewPager view_Pager;
    Timer timer;
    private int currentPage = 0;
    //Material Searchview
    private SearchListAdapter searchListAdapter;
    private MaterialSearchView materialSearchView;
    private LinearLayout searchList;
    private ArrayList<Category> suggestList = new ArrayList<>();

    Toolbar toolbar;
    private TextView textView;
    public RecyclerView department_RecyclerView, ProductInSale_RecyclerView;
    LinearLayoutManager layoutManager;
  //  private CounterFab fb_ShoppingBasket;
    Intent intent;
    LoginAlert loginAlert;

    SharedPreferences sharedPreferences, sharedPref;
    public static final String MyPREFERENCES = "User" ;
    public static final String CartPREFERENCES = "Cart" ;
    public static final String count = "count";
    public static final String Name = "UserNameKey";
    public static final String Userid = "UseridKey";
    private String  ProductID = "", UserID="";
    private  int cartCount= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        //Firebase Database
        deptDatabase = FirebaseDatabase.getInstance().getReference("Department");
        saleDatabase = FirebaseDatabase.getInstance().getReference("Products");
        categoryDatabase = FirebaseDatabase.getInstance().getReference("Category");

        //Shared preference
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        UserID = (sharedPreferences.getString(Userid, ""));
        sharedPref = getSharedPreferences(CartPREFERENCES, Context.MODE_PRIVATE);
        cartCount =  (sharedPref.getInt(count, 0));


        // prodDatabase = FirebaseDatabase.getInstance().getReference("Products");

        //Auto Pager
        view_Pager = (ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter viewpageradapter = new ViewPagerAdapter(this);
        view_Pager.setAdapter(viewpageradapter);
        setupAutoPager();

        //ToolBar
        toolbar = (Toolbar) findViewById(R.id.searchtoolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle("Home Decore");
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


        //Tagline taxBox
        textView = (TextView) findViewById(R.id.textviewmarquee);
        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView.setSelected(true);
        textView.setSingleLine();

        //Bottom navigation
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewHolder());

        BottomNavigationViewHolder.disableShiftMode(bottomNavigationView);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_myFavoutite:
                        intent = new Intent(StoreActivity.this,FavouriteActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_home:
                        intent = new Intent(StoreActivity.this, StoreActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_myAccount:
                        intent = new Intent(StoreActivity.this,UserAccountActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_sale:
                        intent = new Intent(StoreActivity.this,ProductInSaleActivity.class);
                        startActivity(intent);

                        break;
                }
                return true;
            }
        });

        //Load Department
        load_Department();

        //load sales
        load_productInSaleItems();

        //load new Arrival
        load_productNewArrival();


        //Search view
        categoryDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    Category category = categorySnapshot.getValue(Category.class);
                    suggestList.add(category);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        load_SearchItems();

    }


    public  void load_Department(){

        //Recycler View
        department_RecyclerView = (RecyclerView)findViewById(R.id.recycle_dept);
        department_RecyclerView.setHasFixedSize(true);
        department_RecyclerView.setNestedScrollingEnabled(false);

        layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        department_RecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        FirebaseRecyclerAdapter<Department,DepartmentViewHolder> adapter = new FirebaseRecyclerAdapter<Department, DepartmentViewHolder>(Department.class,R.layout.department_layout,DepartmentViewHolder.class,deptDatabase) {
            @Override
            protected void populateViewHolder(DepartmentViewHolder viewHolder, final Department model, int position) {
                viewHolder.department_Name.setText(model.getDepartmentName());
                Picasso.with(getBaseContext()).load(model.getDepartmentImage()).into(viewHolder.department_Image);
                Department clickitem = model;
                viewHolder.setClickListener(new DepartmentViewHolder.ItemClickListener() {
                    @Override
                    public void onClickItem(int pos) {
                        String name = model.getDepartmentName();
                        if(name.equals("Sales"))
                        {
                            Intent intent = new Intent(StoreActivity.this, ProductInSaleActivity.class);
                                startActivity(intent);
                        }
                        else {
                            Intent intent = new Intent(StoreActivity.this, CategoryActivity.class);
                            intent.putExtra("DeptName", model.getDepartmentName());
                            intent.putExtra("DeptID", model.getDepartmentID());
                            startActivity(intent);
                        }
                    }
                });

            }
        };
        department_RecyclerView.setAdapter(adapter);

    }

    public void load_productInSaleItems() {
        //Recycler View
        ProductInSale_RecyclerView = (RecyclerView)findViewById(R.id.recycle_sale);
        ProductInSale_RecyclerView.setHasFixedSize(true);
        ProductInSale_RecyclerView.setNestedScrollingEnabled(false);
        ProductInSale_RecyclerView.setLayoutManager(new GridLayoutManager(this, 2));



        FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(Product.class,R.layout.product_sale_layout,ProductViewHolder.class,saleDatabase.orderByChild("ProductSale").equalTo("Sale").limitToFirst(2)) {
            @Override
            protected void populateViewHolder(ProductViewHolder viewHolder, final Product model, int position) {
                Picasso.with(getBaseContext()).load(model.getProductImage()).into(viewHolder.product_Image);
                viewHolder.product_Name.setText(model.getProductName());


                viewHolder.product_saleLimit.setText(" " + model.getProductSaleLimit() + " % off");

                viewHolder.product_saleEndDate.setText("Ends "+model.getProductSaleEndDate());

                viewHolder.setClickListener(new ProductViewHolder.ItemClickListener() {
                    @Override
                    public void onClickItem(View view, int pos, boolean b) {
                        Intent intent = new Intent(StoreActivity.this, ProductDetailActivity.class);
                        intent.putExtra("ProductID", model.getProductID());
                        startActivity(intent);
                    }

                    @Override
                    public void onClick(View view, int adapterPosition, boolean b) {

                    }
                });
            }
        };

        ProductInSale_RecyclerView.setAdapter(adapter);

    }

    public void load_productNewArrival(){
        //Recycler View
        ProductInSale_RecyclerView = (RecyclerView)findViewById(R.id.recycle_newArrival);
        ProductInSale_RecyclerView.setHasFixedSize(true);
        ProductInSale_RecyclerView.setNestedScrollingEnabled(false);
        ProductInSale_RecyclerView.setLayoutManager(new GridLayoutManager(this, 2));



        FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(Product.class,R.layout.product_sale_layout,ProductViewHolder.class,saleDatabase.orderByChild("ProductSale").equalTo("New Arrival").limitToFirst(2)) {
            @Override
            protected void populateViewHolder(ProductViewHolder viewHolder, final Product model, int position) {
                Picasso.with(getBaseContext()).load(model.getProductImage()).into(viewHolder.product_Image);
                viewHolder.product_Name.setText(model.getProductName());


                viewHolder.product_saleLimit.setText("New Arrival");

               // viewHolder.product_saleEndDate.setText("Ends "+model.getProductSaleEndDate());

                viewHolder.setClickListener(new ProductViewHolder.ItemClickListener() {
                    @Override
                    public void onClickItem(View view, int pos, boolean b) {
                        Intent intent = new Intent(StoreActivity.this, ProductDetailActivity.class);
                        intent.putExtra("ProductID", model.getProductID());
                        startActivity(intent);
                    }

                    @Override
                    public void onClick(View view, int adapterPosition, boolean b) {

                    }
                });
            }
        };

        ProductInSale_RecyclerView.setAdapter(adapter);

    }

    public void load_SearchItems(){

        materialSearchView = (MaterialSearchView) findViewById(R.id.search_view);
        materialSearchView.setEllipsize(true);
        materialSearchView.setVoiceSearch(true);

      //  String[] suggestionlist = new String[searchString.size()];
       // suggestionlist = searchString.toArray(suggestionlist);

     //   materialSearchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));

        Intent intent  = getIntent();

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
        }

        searchList = (LinearLayout) findViewById(R.id.search_listlayout);

        final ListView listView = (ListView) findViewById(R.id.list_Search);

        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                    searchList.setVisibility(View.VISIBLE);
                    materialSearchView.setVisibility(View.VISIBLE);
                // remove back arrow to toolbar
                if (getSupportActionBar() != null){
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setDisplayShowHomeEnabled(false);
                }
            }

            @Override
            public void onSearchViewClosed() {
                searchList.setVisibility(View.GONE);
                materialSearchView.setVisibility(View.GONE);
                // add back arrow to toolbar
                if (getSupportActionBar() != null){
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                }

            }
        });

        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null && !newText.isEmpty()){
                    ArrayList<Category> lstfound = new ArrayList<Category>();
                    for(Category item:suggestList){
                       // item.getCategoryName().cont
                        if(item.getCategoryName().contains(newText))
                            lstfound.add(item);
                    }

                    searchListAdapter = new SearchListAdapter(StoreActivity.this, lstfound);
                    listView.setAdapter(searchListAdapter);

                }

                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(StoreActivity.this, ProductListActivity.class);
                intent.putExtra("CategoryID",searchListAdapter.getItem(position).getCategoryID() );
                intent.putExtra("CategoryName", searchListAdapter.getItem(position).getCategoryName());
                startActivity(intent);
            }
        });


    }

    public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
        public final static String AUTHORITY = "com.example.MySuggestionProvider";
        public final static int MODE = DATABASE_MODE_QUERIES;

        public MySuggestionProvider() {
            setupSuggestions(AUTHORITY, MODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    materialSearchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onBackPressed() {
        if (materialSearchView.isSearchOpen()) {
            materialSearchView.closeSearch();

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_item_menu, menu);
          MenuItem item = menu.findItem(R.id.action_search);
          materialSearchView.setMenuItem(item);
          item = menu.findItem(R.id.action_cart);
          LayerDrawable icon = (LayerDrawable) item.getIcon();
          if(!UserID.isEmpty() && !UserID.equals(null)) {
              setBadgeCount(this, icon, String.valueOf(cartCount));
          }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_search:
                return true;
            case R.id.action_cart:
                if(!UserID.isEmpty() && !UserID.equals(null)) {
                    Intent intent = new Intent(StoreActivity.this, Cart.class);
                    startActivity(intent);
                }
                else {
                    loginAlert = new LoginAlert(StoreActivity.this);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    private void setupAutoPager()
    {
        view_Pager.setCurrentItem(0);

        // Timer for auto sliding
        timer  = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(currentPage<=5){
                            view_Pager.setCurrentItem(currentPage);
                            currentPage++;
                        }else{
                            currentPage = 0;
                            view_Pager.setCurrentItem(currentPage);
                        }
                    }
                });
            }
        }, 500, 3000);
    }


}
