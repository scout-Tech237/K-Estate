package com.scout.k_estates.User.ViewItems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.scout.k_estates.HelperClasses.MyPropertiesAdapter;
import com.scout.k_estates.HelperClasses.MyPropertiesModel;
import com.scout.k_estates.R;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    List<MyPropertiesModel> myPropertiesModelList;
    ListView listView;
    SearchView searchView;
    MyPropertiesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search);

        //remomve landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //get category
        String category = getIntent().getStringExtra("Category");

        //hooks
        listView = findViewById(R.id.list_search);
        searchView = findViewById(R.id.search_view);
        mDatabase = FirebaseDatabase.getInstance();
        myPropertiesModelList = new ArrayList<>();
        mRef = mDatabase.getReference().child("Real Estate Items").child(category);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myPropertiesModelList.clear();

                for (DataSnapshot myPropList: snapshot.getChildren()){

                    MyPropertiesModel myPropertiesModel = myPropList.getValue(MyPropertiesModel.class);
                    myPropertiesModelList.add(myPropertiesModel);
                }

                adapter = new MyPropertiesAdapter(Search.this, myPropertiesModelList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processSearch(newText);
                return false;
            }
        });
    }

    private void processSearch(String newText) {

        String userinput = newText.toLowerCase();
        List<MyPropertiesModel> newList = new ArrayList();
        for(MyPropertiesModel name: myPropertiesModelList)
        {
            if(name.getRegion().toLowerCase().contains(userinput)){
                newList.add(name);
            }

        }

        adapter = new MyPropertiesAdapter(Search.this, newList);
        listView.setAdapter(adapter);

    }
}