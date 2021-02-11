package com.scout.k_estates.LocationOwner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.scout.k_estates.HelperClasses.MyPropertiesAdapter;
import com.scout.k_estates.HelperClasses.MyPropertiesModel;
import com.scout.k_estates.HelperClasses.SessionManager;
import com.scout.k_estates.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyProperties extends AppCompatActivity {

    FirebaseDatabase mDatabase;
    DatabaseReference mRef, mRef1, mRef2, mRef3;

    String phoneNum;
    SessionManager sessionManager;

    ListView listView, listView1, listView2, listView3;
    List<MyPropertiesModel> myPropertiesModelList, myPropertiesModelList1, myPropertiesModelList2, myPropertiesModelList3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_properties);

        //remomve landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //hooks
        listView = findViewById(R.id.my_prop_list_view);
        listView1 = findViewById(R.id.my_prop_list_view1);
        listView2 = findViewById(R.id.my_prop_list_view2);
        listView3 = findViewById(R.id.my_prop_list_view3);
        myPropertiesModelList = new ArrayList<>();
        myPropertiesModelList1 = new ArrayList<>();
        myPropertiesModelList2 = new ArrayList<>();
        myPropertiesModelList3 = new ArrayList<>();


        //set retieve data from db here
        sessionManager = new SessionManager(MyProperties.this, SessionManager.SESSION_USERSESSION);
        HashMap<String, String> userDetails = sessionManager.getUsersDetailFromSession();

        phoneNum = userDetails.get(SessionManager.KEY_PHONENUMBER);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("Real Estate Items").child("Apartments");
        mRef1 = mDatabase.getReference().child("Real Estate Items").child("Guest Houses");
        mRef2 = mDatabase.getReference().child("Real Estate Items").child("Business Places");
        mRef3 = mDatabase.getReference().child("Real Estate Items").child("Plots");

        Query query = mRef.orderByChild("PhoneNum").equalTo(phoneNum);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myPropertiesModelList.clear();

                for (DataSnapshot myPropList: snapshot.getChildren()){

                    MyPropertiesModel myPropertiesModel = myPropList.getValue(MyPropertiesModel.class);
                    myPropertiesModelList.add(myPropertiesModel);
                }

                MyPropertiesAdapter adapter = new MyPropertiesAdapter(MyProperties.this, myPropertiesModelList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query query1 = mRef1.orderByChild("PhoneNum").equalTo(phoneNum);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myPropertiesModelList1.clear();

                for (DataSnapshot myPropList: snapshot.getChildren()){

                    MyPropertiesModel myPropertiesModel = myPropList.getValue(MyPropertiesModel.class);
                    myPropertiesModelList1.add(myPropertiesModel);
                }

                MyPropertiesAdapter adapter = new MyPropertiesAdapter(MyProperties.this, myPropertiesModelList1);
                listView1.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query query2 = mRef2.orderByChild("PhoneNum").equalTo(phoneNum);
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myPropertiesModelList2.clear();

                for (DataSnapshot myPropList1: snapshot.getChildren()){

                    MyPropertiesModel myPropertiesModel = myPropList1.getValue(MyPropertiesModel.class);
                    myPropertiesModelList2.add(myPropertiesModel);
                }

                MyPropertiesAdapter adapter = new MyPropertiesAdapter(MyProperties.this, myPropertiesModelList2);
                listView2.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query query3 = mRef3.orderByChild("PhoneNum").equalTo(phoneNum);
        query3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myPropertiesModelList3.clear();

                for (DataSnapshot myPropList1: snapshot.getChildren()){

                    MyPropertiesModel myPropertiesModel = myPropList1.getValue(MyPropertiesModel.class);
                    myPropertiesModelList3.add(myPropertiesModel);
                }

                MyPropertiesAdapter adapter = new MyPropertiesAdapter(MyProperties.this, myPropertiesModelList3);
                listView3.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }





}