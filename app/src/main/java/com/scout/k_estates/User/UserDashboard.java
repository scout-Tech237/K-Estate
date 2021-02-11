package com.scout.k_estates.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.scout.k_estates.BuildConfig;
import com.scout.k_estates.HelperClasses.CategoryHelperClass;
import com.scout.k_estates.HelperClasses.CategoryRecyclerviewAdapter;
import com.scout.k_estates.HelperClasses.MyPropertiesAdapter;
import com.scout.k_estates.HelperClasses.MyPropertiesModel;
import com.scout.k_estates.HelperClasses.SessionManager;
import com.scout.k_estates.LocationOwner.Authentication.RetailerStartUpScreen;
import com.scout.k_estates.LocationOwner.CreateAccomodation.CreateItem1;
import com.scout.k_estates.LocationOwner.MyProperties;
import com.scout.k_estates.Others.AboutUs;
import com.scout.k_estates.Others.RateUs;
import com.scout.k_estates.R;
import com.scout.k_estates.User.ViewItems.AllCategories;
import com.scout.k_estates.User.ViewItems.Search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //VARIABLES
    static final float END_SCALE = 0.7f;

    ListView listView;
    List<MyPropertiesModel> myPropertiesModelList;
    //new data
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;

    FirebaseAuth firebaseAuth;

    ImageView menuIcon;
    LinearLayout contentView;
    String phoneNum;
    SessionManager sessionManager;
    boolean isLoggedIn;
    RecyclerView categoryRecyclerview;
    RecyclerView.Adapter adapter;
    CategoryRecyclerviewAdapter.RecyclerviewClickListener listener;

    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ArrayList<CategoryHelperClass> categories;

    //private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);


        //ads initialised
        /*MobileAds.initialize(this, "ca-app-pub-4007973912036720~4080025246");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        //remomve landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        // new data
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("Real Estate Items").child("Apartments");
        mStorage = FirebaseStorage.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();

        //hooks
        categoryRecyclerview = findViewById(R.id.userdashboard_recyclerview);
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);
        listView = findViewById(R.id.user_dashboard_recyclerview);
        myPropertiesModelList = new ArrayList<>();

        mRef.orderByChild("UId").limitToFirst(20).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myPropertiesModelList.clear();

                for (DataSnapshot myPropList: snapshot.getChildren()){

                    MyPropertiesModel myPropertiesModel = myPropList.getValue(MyPropertiesModel.class);
                    myPropertiesModelList.add(myPropertiesModel);
                }

                MyPropertiesAdapter adapter = new MyPropertiesAdapter(UserDashboard.this, myPropertiesModelList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //checking session
        sessionManager = new SessionManager(UserDashboard.this, SessionManager.SESSION_USERSESSION);
        HashMap<String, String> userDetails = sessionManager.getUsersDetailFromSession();

        phoneNum = userDetails.get(SessionManager.KEY_PHONENUMBER);
        isLoggedIn = sessionManager.checkLogin();

        //Drawer Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);


        categoryRecyclerview();
        navigationDrawer();
        hideItem();

    }

    private void categoryRecyclerview() {
        categoryRecyclerview.setHasFixedSize(true);
        categoryRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        categories = new ArrayList<>();

        categories.add(new CategoryHelperClass(R.drawable.house_pic, "Apartments"));
        categories.add(new CategoryHelperClass(R.drawable.rest_house, "Guest Houses"));
        categories.add(new CategoryHelperClass(R.drawable.bussiness_building, "Business Places"));
        categories.add(new CategoryHelperClass(R.drawable.plot, "Plots"));

        setOnClickListener();

        adapter = new CategoryRecyclerviewAdapter(categories, listener);
        categoryRecyclerview.setAdapter(adapter);

    }

    private void setOnClickListener() {

        listener = (v, position) -> {
            switch ((categories.get(position).getTitle())) {
                case "Apartments":
                    Intent intent = new Intent(getApplicationContext(), Search.class);
                    String category = "Apartments";
                    intent.putExtra("Category", category);
                    startActivity(intent);
                    break;
                case "Guest Houses":
                    Intent intent3 = new Intent(getApplicationContext(), Search.class);
                    String category3 = "Guest Houses";
                    intent3.putExtra("Category", category3);
                    startActivity(intent3);
                    break;
                case "Business Places":
                    Intent intent2 = new Intent(getApplicationContext(), Search.class);
                    String category2 = "Business Places";
                    intent2.putExtra("Category", category2);
                    startActivity(intent2);
                    break;
                case "Plots":
                    Intent intent1 = new Intent(getApplicationContext(), Search.class);
                    String category1 = "Plots";
                    intent1.putExtra("Category", category1);
                    startActivity(intent1);
                    break;
            }
        };

    }

    //Navigation Drawers Functions
    private void navigationDrawer() {

        //Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(view -> {
            if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {


        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //Scale the View Based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                //Translating the view, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);

            }

        });
    }

    public void callRetailerScreen(View view){

        Intent intent = new Intent(getApplicationContext(), RetailerStartUpScreen.class);
        Intent intent1= new Intent(getApplicationContext(), CreateItem1.class);

        if (phoneNum != null){
            startActivity(intent1);
        }else {
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void hideItem() {
        Menu nav_Menu = navigationView.getMenu();

        if (!sessionManager.checkLogin()){
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
            nav_Menu.findItem(R.id.nav_my_properties).setVisible(false);
            nav_Menu.findItem(R.id.nav_login).setVisible(true);
        } else {
            nav_Menu.findItem(R.id.nav_login).setVisible(false);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.nav_all_categories){
            Intent intent = new Intent(getApplicationContext(), AllCategories.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_logout){
            Intent intent1 = new Intent(getApplicationContext(), UserDashboard.class);
            sessionManager.logoutUserFromSession();
            startActivity(intent1);
        } else if (item.getItemId() == R.id.nav_my_properties){
            Intent intent2 = new Intent(getApplicationContext(), MyProperties.class);
            intent2.putExtra("phoneNum", phoneNum);
            startActivity(intent2);
        } else if (item.getItemId() == R.id.nav_home){
            Intent intent3 = new Intent(getApplicationContext(), UserDashboard.class);
            startActivity(intent3);
        } else if (item.getItemId() == R.id.nav_add_properties){
            callRetailerScreen(null);
        } else if (item.getItemId() == R.id.nav_about_us){
            Intent intent4 = new Intent(getApplicationContext(), AboutUs.class);
            startActivity(intent4);
        }else if (item.getItemId() == R.id.nav_share){
            //share app
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else if (item.getItemId() == R.id.nav_rate_us){
            Intent intent6 = new Intent(getApplicationContext(), RateUs.class);
            startActivity(intent6);
        } else if (item.getItemId() == R.id.nav_login){
            Intent intent7 = new Intent(getApplicationContext(), RetailerStartUpScreen.class);
            startActivity(intent7);
        }

        /*switch (item.getItemId()) {

            case R.id.nav_all_categories:
                Intent intent = new Intent(getApplicationContext(), AllCategories.class);
                startActivity(intent);
                break;

            case R.id.nav_logout:
                Intent intent1 = new Intent(getApplicationContext(), UserDashboard.class);
                sessionManager.logoutUserFromSession();
                startActivity(intent1);
                break;

            case R.id.nav_my_properties:
                Intent intent2 = new Intent(getApplicationContext(), MyProperties.class);
                intent2.putExtra("phoneNum", phoneNum);
                startActivity(intent2);
                break;

            case R.id.nav_home:
                Intent intent3 = new Intent(getApplicationContext(), UserDashboard.class);
                startActivity(intent3);
                break;

            case R.id.nav_add_properties:
                callRetailerScreen(null);
                break;

            case R.id.nav_about_us:
                Intent intent4 = new Intent(getApplicationContext(), AboutUs.class);
                startActivity(intent4);
                break;

            case R.id.nav_share:
                //share app
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;

            case R.id.nav_rate_us:
                Intent intent6 = new Intent(getApplicationContext(), RateUs.class);
                startActivity(intent6);
                break;

            case R.id.nav_login:
                Intent intent7 = new Intent(getApplicationContext(), RetailerStartUpScreen.class);
                startActivity(intent7);
                break;

        }*/

        return true;
    }

    public void search(View view) {
        startActivity(new Intent(getApplicationContext(), AllCategories.class));
    }
}