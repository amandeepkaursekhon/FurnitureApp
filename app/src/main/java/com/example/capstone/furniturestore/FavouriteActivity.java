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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.andremion.counterfab.CounterFab;
import com.example.capstone.furniturestore.Adapter.FavouriteAdapter;
import com.example.capstone.furniturestore.Adapter.SearchListAdapter;
import com.example.capstone.furniturestore.Alert.LoginAlert;
import com.example.capstone.furniturestore.Database.Database;
import com.example.capstone.furniturestore.Helper.BadgeDrawable;
import com.example.capstone.furniturestore.Models.Category;
import com.example.capstone.furniturestore.Models.Favourite;
import com.example.capstone.furniturestore.Models.Product;
import com.example.capstone.furniturestore.ViewHolder.BottomNavigationViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {

    //Database
    private DatabaseReference favouriteDatabase, productDatabase,categoryDatabase;

    //Toolbar
    Toolbar toolbar;

    //Recycler
    public RecyclerView favourite_RecyclerView;
    FavouriteAdapter fav_adapter;
    LinearLayoutManager layoutManager;
    Context context;
    final List<String> listProductID = new ArrayList<String>();
    final List<String> ProductID = new ArrayList<String>();

    ArrayList<Product> productList = new ArrayList<Product>();
    ArrayList<String>favouriteList = new ArrayList<>();

    SharedPreferences sharedPreferences, sharedPref;
    public static final String MyPREFERENCES = "User" ;
    public static final String CartPREFERENCES = "Cart" ;
    public static final String count = "count";
    public static final String Name = "UserNameKey";
    public static final String Userid = "UseridKey";
    private String  UserID="", UserName="";
    private  int cartCount= 0;

    //material search
    private SearchListAdapter searchListAdapter;
    private MaterialSearchView materialSearchView;
    private LinearLayout searchList;
    private ArrayList<Category> suggestList = new ArrayList<>();

   // CounterFab fb_ShoppingBasket;

    LoginAlert loginAlert;
    Button btnEdit;
    TextView txtcountitem;
    int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        context = getBaseContext();

        //Shared preference
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        UserID = (sharedPreferences.getString(Userid, ""));
        sharedPref = getSharedPreferences(CartPREFERENCES, Context.MODE_PRIVATE);
        cartCount =  (sharedPref.getInt(count, 0));
        UserName = (sharedPreferences.getString(Name, ""));


        //Database
        favouriteDatabase = FirebaseDatabase.getInstance().getReference("Favourites").child(UserID);
        productDatabase = FirebaseDatabase.getInstance().getReference("Products");
        categoryDatabase = FirebaseDatabase.getInstance().getReference("Category");

        //toolBar settings
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle(" My Favourite");

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

        txtcountitem = (TextView) findViewById(R.id.txt_favouriteItem);

        //EditButton Favourite item
        btnEdit = (Button) findViewById(R.id.btn_Editfavourite);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavouriteActivity.this, EditFavouriteActivity.class);
                intent.putExtra("UserID", UserID);
                startActivity(intent);
            }
        });


     /*   fb_ShoppingBasket = (CounterFab) findViewById(R.id.fb_ShoppingBasket);

        fb_ShoppingBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavouriteActivity.this, Cart.class);
                startActivity(intent);

            }
        });
        fb_ShoppingBasket.setCount(new Database(this).getCountCart());
*/

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
                        Intent intent = new Intent(FavouriteActivity.this, FavouriteActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_home:
                         intent = new Intent(FavouriteActivity.this, StoreActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_myAccount:
                        intent = new Intent(FavouriteActivity.this,UserAccountActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_sale:
                        intent = new Intent(FavouriteActivity.this,ProductInSaleActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        LinearLayout loginLayout = (LinearLayout) findViewById(R.id.loginLayout);

        Button btnlogin = (Button) findViewById(R.id.btnlogin);
        Button btnsignin = (Button) findViewById(R.id.btnSignin);

        if(!UserID.isEmpty() && UserID != null)
        {
            //Display Favourite Items
            load_Favourite();
        }
        else {

            loginLayout.setVisibility(View.VISIBLE);


            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FavouriteActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            });

            btnsignin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FavouriteActivity.this,SignUpActivity.class);
                    startActivity(intent);
                }
            });
            btnEdit.setVisibility(View.INVISIBLE);
        }


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

    public  void load_Favourite() {

        favouriteDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                counter = 0;
                favouriteList.clear();
                productList.clear();
                for (DataSnapshot favouriteSnapshot : dataSnapshot.getChildren()) {
                    Favourite fav_product = favouriteSnapshot.getValue(Favourite.class);
                    String id = fav_product.getProduct_ID();

                    listProductID.add(fav_product.getProduct_ID());
                    favouriteList.add(fav_product.getFavorite_ID());

                    productDatabase.orderByChild("ProductID").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                                Product product = productSnapshot.getValue(Product.class);
                                productList.add(product);
                                counter++;
                            }

                                txtcountitem.setText(counter + " Item");
                                fav_adapter = new FavouriteAdapter(productList, FavouriteActivity.this);
                                favourite_RecyclerView = (RecyclerView) findViewById(R.id.recycle_Favourite);
                                favourite_RecyclerView.setHasFixedSize(true);
                                favourite_RecyclerView.setNestedScrollingEnabled(false);
                                layoutManager = new LinearLayoutManager(getBaseContext());
                                favourite_RecyclerView.setLayoutManager(new GridLayoutManager(FavouriteActivity.this, 2));
                                favourite_RecyclerView.setAdapter(fav_adapter);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

    public void load_SearchItems(){

        materialSearchView = (MaterialSearchView) findViewById(R.id.search_view);
        materialSearchView.setEllipsize(true);
        materialSearchView.setVoiceSearch(true);

        Intent intent  = getIntent();

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    StoreActivity.MySuggestionProvider.AUTHORITY, StoreActivity.MySuggestionProvider.MODE);
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

                    searchListAdapter = new SearchListAdapter(FavouriteActivity.this, lstfound);
                    listView.setAdapter(searchListAdapter);

                }

                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(FavouriteActivity.this, ProductListActivity.class);
                intent.putExtra("CategoryID",searchListAdapter.getItem(position).getCategoryID() );
                intent.putExtra("CategoryName", searchListAdapter.getItem(position).getCategoryName());
                startActivity(intent);
            }
        });


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
                    Intent intent = new Intent(FavouriteActivity.this, Cart.class);
                    startActivity(intent);
                }
                else {
                    loginAlert = new LoginAlert(FavouriteActivity.this);
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


}
