package com.example.capstone.furniturestore;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.provider.SearchRecentSuggestions;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
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

import com.andremion.counterfab.CounterFab;
import com.example.capstone.furniturestore.Alert.LoginAlert;
import com.example.capstone.furniturestore.CurrentUser.CurrentUser;
import com.example.capstone.furniturestore.Database.Database;
import android.widget.TextView;
import com.andremion.counterfab.CounterFab;
import com.example.capstone.furniturestore.Adapter.SearchListAdapter;
import com.example.capstone.furniturestore.Database.Database;
import com.example.capstone.furniturestore.Helper.BadgeDrawable;
import com.example.capstone.furniturestore.Models.Category;
import com.example.capstone.furniturestore.ViewHolder.BottomNavigationViewHolder;
import com.example.capstone.furniturestore.ViewHolder.BottomNavigationViewHolder;
import com.example.capstone.furniturestore.ViewHolder.BottomNavigationViewHolder;
import com.example.capstone.furniturestore.ViewHolder.CategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    //Database reference
    private DatabaseReference categoryDatabase;

    //material searchview
    private SearchListAdapter searchListAdapter;
    private MaterialSearchView materialSearchView;
    private LinearLayout searchList;
    private ArrayList<Category> suggestList = new ArrayList<>();

    Toolbar toolbar;
    public RecyclerView category_RecyclerView;
    LinearLayoutManager layoutManager;
    // private CounterFab fb_ShoppingBasket;
    private TextView txt_deptName;
    private String department_ID, department_Name;

    SharedPreferences sharedPreferences, sharedPref;
    public static final String MyPREFERENCES = "User" ;
    public static final String CartPREFERENCES = "Cart" ;
    public static final String count = "count";
    public static final String Name = "UserNameKey";
    public static final String Userid = "UseridKey";
    private String  ProductID = "", UserID="";
    private  int cartCount= 0;
    LoginAlert loginAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //FireBase
        categoryDatabase = FirebaseDatabase.getInstance().getReference("Category");

        //Shared preference
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        UserID = (sharedPreferences.getString(Userid, ""));
        sharedPref = getSharedPreferences(CartPREFERENCES, Context.MODE_PRIVATE);
        cartCount =  (sharedPref.getInt(count, 0));


        //toolBar settings
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle(" Category");

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


      /*  //Floating Button
        fb_ShoppingBasket = (CounterFab) findViewById(R.id.fb_ShoppingBasket);

        fb_ShoppingBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, Cart.class);
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
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent intent = new Intent(CategoryActivity.this, StoreActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_myFavoutite:
                        intent = new Intent(CategoryActivity.this, FavouriteActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_myAccount:
                        intent = new Intent(CategoryActivity.this, UserAccountActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_sale:
                        intent = new Intent(CategoryActivity.this, ProductInSaleActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });


        //Recycler View
        category_RecyclerView = (RecyclerView) findViewById(R.id.recycle_category);
        category_RecyclerView.setHasFixedSize(true);
        category_RecyclerView.setNestedScrollingEnabled(false);
        layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        category_RecyclerView.setLayoutManager(new GridLayoutManager(this, 1));


        // Get Intent here
        if (getIntent() != null) {
            Intent i = getIntent();
            department_ID = i.getExtras().getString("DeptID");
            department_Name = i.getExtras().getString("DeptName");
            txt_deptName = (TextView) findViewById(R.id.txt_departmentName);
            txt_deptName.setText(department_Name);

        }
        if (!department_ID.isEmpty() && department_ID != null) {
            load_Category();
        }


        categoryDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
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

    public void load_Category() {

        FirebaseRecyclerAdapter<Category, CategoryViewHolder> adapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(Category.class, R.layout.category_layout, CategoryViewHolder.class, categoryDatabase.orderByChild("CategoryDepartmentID").equalTo(department_ID)) {
            @Override
            protected void populateViewHolder(CategoryViewHolder viewHolder, final Category model, int position) {
                viewHolder.Category_Name.setText(model.getCategoryName());
                Picasso.with(getBaseContext()).load(model.getCategoryImage()).into(viewHolder.Category_Image);

                viewHolder.setClickListener(new CategoryViewHolder.ItemClickListener() {
                    @Override
                    public void onClickItem(int pos) {
                        Intent intent = new Intent(CategoryActivity.this, ProductListActivity.class);
                        intent.putExtra("CategoryID", model.getCategoryID());
                        intent.putExtra("CategoryName", model.getCategoryName());
                        startActivity(intent);
                    }
                });
            }
        };
        category_RecyclerView.setAdapter(adapter);
    }

    public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
        public final static String AUTHORITY = "com.example.MySuggestionProvider";
        public final static int MODE = DATABASE_MODE_QUERIES;

        public MySuggestionProvider() {
            setupSuggestions(AUTHORITY, MODE);
        }
    }

    public void load_SearchItems() {

        materialSearchView = (MaterialSearchView) findViewById(R.id.search_view);
        materialSearchView.setEllipsize(true);
        materialSearchView.setVoiceSearch(true);

        //  String[] suggestionlist = new String[searchString.size()];
        // suggestionlist = searchString.toArray(suggestionlist);

        //   materialSearchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));

        Intent intent = getIntent();

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
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setDisplayShowHomeEnabled(false);
                }
            }

            @Override
            public void onSearchViewClosed() {
                searchList.setVisibility(View.GONE);
                materialSearchView.setVisibility(View.GONE);
                // add back arrow to toolbar
                if (getSupportActionBar() != null) {
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
                if (newText != null && !newText.isEmpty()) {
                    ArrayList<Category> lstfound = new ArrayList<Category>();
                    for (Category item : suggestList) {
                        // item.getCategoryName().cont
                        if (item.getCategoryName().contains(newText))
                            lstfound.add(item);
                    }
                    searchListAdapter = new SearchListAdapter(CategoryActivity.this, lstfound);
                    listView.setAdapter(searchListAdapter);

                }

                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(CategoryActivity.this, ProductListActivity.class);
                intent.putExtra("CategoryID", searchListAdapter.getItem(position).getCategoryID());
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
        if (!UserID.isEmpty() && !UserID.equals(null)) {
            setBadgeCount(this, icon, String.valueOf(cartCount));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_cart:
                if(!UserID.isEmpty() && !UserID.equals(null)) {
                    Intent intent = new Intent(CategoryActivity.this, Cart.class);
                    startActivity(intent);
                }
                else {
                    loginAlert = new LoginAlert(CategoryActivity.this);
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
