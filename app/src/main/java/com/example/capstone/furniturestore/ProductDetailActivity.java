package com.example.capstone.furniturestore;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.provider.SearchRecentSuggestions;
import android.speech.RecognizerIntent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andremion.counterfab.CounterFab;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.capstone.furniturestore.ProductARActivity.INTENT_PRODUCT_KEY;


public class ProductDetailActivity extends AppCompatActivity {

    private static final String TAG = "ProductActivity";
    //Database reference
    private DatabaseReference productDatabase,favouriteDatabase,categoryDatabase;

    //shared preference
    SharedPreferences sharedPreferences, sharedPref;
    public static final String MyPREFERENCES = "User" ;
    public static final String CartPREFERENCES = "Cart" ;
    public static final String count = "count";
    public static final String Name = "UserNameKey";
    public static final String Userid = "UseridKey";

    //material search
    private SearchListAdapter searchListAdapter;
    private MaterialSearchView materialSearchView;
    private LinearLayout searchList;
    private ArrayList<Category> suggestList = new ArrayList<>();

    Toolbar toolbar;
   // CounterFab fb_ShoppingBasket;
    private TextView txtProductName,txtProductMenufacturer, txtProductSalePrice, txtProductPrice, txtProductShipping ;
    private ImageView imgProduct;
    private Button btnAddToCart, btnAddToFavourite, btnView;
    private Product current_product;

    private String  ProductID = "", UserID="";
    private  int cartCount= 0;
    Intent intent;
    LoginAlert loginAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        //Database
        productDatabase =  FirebaseDatabase.getInstance().getReference("Products");
        categoryDatabase = FirebaseDatabase.getInstance().getReference("Category");
        favouriteDatabase = FirebaseDatabase.getInstance().getReference("Favourites");

        //Shared preference
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        UserID = (sharedPreferences.getString(Userid, ""));
        sharedPref = getSharedPreferences(CartPREFERENCES, Context.MODE_PRIVATE);
        cartCount =  (sharedPref.getInt(count, 0));

        imgProduct = (ImageView) findViewById(R.id.imageproduct);
        txtProductName = (TextView) findViewById(R.id.txtproductName);
        txtProductMenufacturer = (TextView) findViewById(R.id.txtproductManufacturer);
        txtProductSalePrice = (TextView) findViewById(R.id.txtproductSalePrice);
        txtProductPrice = (TextView) findViewById(R.id.txtproductPrice);
        txtProductShipping = (TextView) findViewById(R.id.txtproductshipping);

         //toolBar settings
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle(" Product Detail");

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

        btnAddToCart = (Button) findViewById(R.id.btn_AddToCart);

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    current_product.setProductQunt(numberButton.getNumber());
                if(UserID.equals(null) || UserID ==  ""){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            ProductDetailActivity.this);

                    // set title
                    alertDialogBuilder.setTitle("LogIn First");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("LogIn First to see your Account!")
                            .setCancelable(false)

                            .setPositiveButton("LogIn", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int id) {
                                    Intent i = new Intent(ProductDetailActivity.this,LoginActivity.class);
                                    startActivity(i);
                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }
                else
                {
                List<Product> tem=new ArrayList<>();
                tem.addAll(new Database(getApplicationContext()).getCarts());
                boolean isAlready=false;
                for(Product product:tem){
                    if(current_product.getProductID().equalsIgnoreCase(product.getProductID())){
                        isAlready=true;
                    }

                }
                Log.e("==value", String.valueOf(current_product.getProductPrice()));
                if(isAlready){
                    View view = findViewById(android.R.id.content);
                    Snackbar.make(view, "Product is already in the cart..", Snackbar.LENGTH_LONG).show();
                    //Toast.makeText(ProductDetailActivity.this,"product is already in cart",Toast.LENGTH_SHORT).show();
                }
                else {
                    new Database(ProductDetailActivity.this).addToCart((current_product));
                    current_product.setProductImage(current_product.getProductImage());
                    current_product.setProductPrice(current_product.getProductPrice());

                    cartCount = cartCount + 1;

                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt(count , cartCount);
                    editor.apply();
                    // Toast.makeText(ProductDetailActivity.this, "Added", Toast.LENGTH_SHORT).show();
                    View view = findViewById(android.R.id.content);
                    Snackbar.make(view, "Added", Snackbar.LENGTH_LONG).show();
                } }}
        });


        btnAddToFavourite = (Button) findViewById(R.id.btn_favoiurite);

        btnAddToFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ShowAlert();

            }
        });


        //ARCore Integration button
        btnView = (Button)findViewById(R.id.btn_View);

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailActivity.this, ProductARActivity.class);
                intent.putExtra(INTENT_PRODUCT_KEY, "Furniture");
                intent.putExtra("prodName", txtProductName.getText().toString());
                startActivity(intent);
            }
        });


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
                        Intent intent = new Intent(ProductDetailActivity.this, FavouriteActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_home:
                         intent = new Intent(ProductDetailActivity.this, StoreActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_myAccount:
                        intent = new Intent(ProductDetailActivity.this,UserAccountActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_sale:
                        intent = new Intent(ProductDetailActivity.this,ProductInSaleActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        // Get Intent here
        if(getIntent() != null){
            Intent i = getIntent();
            ProductID = i.getExtras().getString("ProductID");

        }
        if(!ProductID.isEmpty() && ProductID != null) {


            productDatabase.orderByChild("ProductID").equalTo(ProductID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Integer i = 0;
                    for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                        Product products = productSnapshot.getValue(Product.class);
                        String prod_id = products.getProductID();

                        current_product = products;
                        Picasso.with(getBaseContext()).load(products.getProductImage()).into(imgProduct);

                        txtProductName.setText(products.getProductName());
                        txtProductMenufacturer.setText(products.getProductManufacturer());
                        txtProductPrice.setText(String.valueOf(products.getProductPrice()));
                        txtProductSalePrice.setText(String.valueOf(products.getProductSalePrice()));
                        if (products.getProductSalePrice() > 75.0) {
                            txtProductShipping.setText("Free Shipping");
                        } else {
                            txtProductShipping.setText("");
                        }

                        i++;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            RelativeLayout layout_info = (RelativeLayout) findViewById(R.id.layout_info);
            layout_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProductDetailActivity.this,ProductInformationActivity.class);
                    intent.putExtra("ProductID", ProductID);
                    intent.putExtra("PageName","ProductInfo");
                    startActivity(intent);
                }
            });

            RelativeLayout layout_specification = (RelativeLayout) findViewById(R.id.layout_specification);
            layout_specification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProductDetailActivity.this,ProductInformationActivity.class);
                    intent.putExtra("ProductID", ProductID);
                    intent.putExtra("PageName","Productspecification");
                    startActivity(intent);
                }
            });

            RelativeLayout layout_shipping = (RelativeLayout) findViewById(R.id.layout_shipping);
            layout_shipping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProductDetailActivity.this,ProductInformationActivity.class);
                    intent.putExtra("ProductID", ProductID);
                    intent.putExtra("PageName","ProductShipping");
                    startActivity(intent);
                }
            });

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
    protected void onResume() {

        super.onResume();
     //   fb_ShoppingBasket.setCount(new Database(this).getCountCart());
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

                    searchListAdapter = new SearchListAdapter(ProductDetailActivity.this, lstfound);
                    listView.setAdapter(searchListAdapter);

                }

                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ProductDetailActivity.this, ProductListActivity.class);
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
                    Intent intent = new Intent(ProductDetailActivity.this, Cart.class);
                    startActivity(intent);
                }
                else {
                    loginAlert = new LoginAlert(ProductDetailActivity.this);
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

    public void ShowAlert(){
        if(UserID.equals(null) || UserID ==  ""){
            loginAlert = new LoginAlert(ProductDetailActivity.this);
        }
        else {

            // get prompts.xml view
            LayoutInflater li = LayoutInflater.from(ProductDetailActivity.this);
            View promptsView = li.inflate(R.layout.add_to_favourite, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    ProductDetailActivity.this);

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);

            final EditText userInput = (EditText) promptsView
                    .findViewById(R.id.editText_title);

            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Add",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    String ID = favouriteDatabase.push().getKey();

                                    if (UserID != "") {
                                        String title = userInput.getText().toString();
                                        Favourite favourite = new Favourite(ProductID, ID, title);
                                        favouriteDatabase.child(UserID).child(ID).setValue(favourite);

                                    }
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }

    }

}
