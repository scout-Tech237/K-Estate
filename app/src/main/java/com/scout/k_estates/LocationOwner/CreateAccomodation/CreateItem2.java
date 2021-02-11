package com.scout.k_estates.LocationOwner.CreateAccomodation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;
import com.scout.k_estates.LocationOwner.CreatePlot.CreatePlot1;
import com.scout.k_estates.R;

import java.util.ArrayList;
import java.util.Objects;

public class CreateItem2 extends AppCompatActivity {

    Spinner region_sp, division_sp;
    TextInputLayout quater;

    ArrayList<String> arrayList_region;
    ArrayAdapter<String> arrayAdapter_region;

    ArrayList<String> arrayList_adamawa,arrayList_far_north,arrayList_north,arrayList_centre,arrayList_east,arrayList_south,arrayList_littoral,arrayList_north_west,arrayList_south_west,arrayList_west;
    ArrayAdapter<String> arrayAdapter_division;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create_item2);

        //hooks
        quater = findViewById(R.id.create_quater);
        region_sp = findViewById(R.id.sp_region);
        division_sp = findViewById(R.id.sp_division);

        //remomve landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //setting spinners for region division and sub division
        arrayList_region = new ArrayList<>();
        arrayList_region.add("Adamawa");
        arrayList_region.add("Far North");
        arrayList_region.add("North");
        arrayList_region.add("Centre");
        arrayList_region.add("East");
        arrayList_region.add("South");
        arrayList_region.add("Littoral");
        arrayList_region.add("North West");
        arrayList_region.add("South West");
        arrayList_region.add("West");

        arrayAdapter_region = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayList_region);
        region_sp.setAdapter(arrayAdapter_region);

        //child spinner code
        arrayList_adamawa  = new ArrayList<>();
        arrayList_adamawa.add("Djérem");
        arrayList_adamawa.add("Faro-et-Déo");
        arrayList_adamawa.add("Mayo-Banyo");
        arrayList_adamawa.add("Mbéré");
        arrayList_adamawa.add("Vina");

        arrayList_far_north = new ArrayList<>();
        arrayList_far_north.add("Diamaré");
        arrayList_far_north.add("Logone-et-Chari");
        arrayList_far_north.add("Mayo-Danay");
        arrayList_far_north.add("Mayo-Kani");
        arrayList_far_north.add("Mayo-Sava");
        arrayList_far_north.add("Mayo-Tsanaga");

        arrayList_north = new ArrayList<>();
        arrayList_north.add("Bénoué");
        arrayList_north.add("Faro");
        arrayList_north.add("Mayo-Louti");
        arrayList_north.add("Mayo-Rey");

        arrayList_centre = new ArrayList<>();
        arrayList_centre.add("Haute-Sanaga");
        arrayList_centre.add("Lekié");
        arrayList_centre.add("Mbam-et-Inoubou");
        arrayList_centre.add("Mbam-et-Kim");
        arrayList_centre.add("Méfou-et-Afamba");
        arrayList_centre.add("Méfou-et-Akono");
        arrayList_centre.add("Mfoundi");
        arrayList_centre.add("Nyong-et-Kéllé");
        arrayList_centre.add("Nyong-et-Mfoumou");
        arrayList_centre.add("Nyong-et-So'o");

        arrayList_east = new ArrayList<>();
        arrayList_east.add("Boumba-et-Ngoko");
        arrayList_east.add("Haut-Nyong");
        arrayList_east.add("Kadey");
        arrayList_east.add("Lom-et-Djerem");

        arrayList_south = new ArrayList<>();
        arrayList_south.add("Dja-et-Lobo");
        arrayList_south.add("Mvila");
        arrayList_south.add("Océan");
        arrayList_south.add("Vallée-du-Ntem");

        arrayList_littoral = new ArrayList<>();
        arrayList_littoral.add("Moungo");
        arrayList_littoral.add("Nkam");
        arrayList_littoral.add("Sanaga-Maritime");
        arrayList_littoral.add("Wouri");

        arrayList_north_west = new ArrayList<>();
        arrayList_north_west.add("Boyo");
        arrayList_north_west.add("Bui");
        arrayList_north_west.add("Donga-Mantung");
        arrayList_north_west.add("Menchum");
        arrayList_north_west.add("Mezam");
        arrayList_north_west.add("Momo");
        arrayList_north_west.add("Ngo-ketunjia");

        arrayList_south_west = new ArrayList<>();
        arrayList_south_west.add("Fako");
        arrayList_south_west.add("Koupé-Manengouba");
        arrayList_south_west.add("Lebialem");
        arrayList_south_west.add("Manyu");
        arrayList_south_west.add("Meme");
        arrayList_south_west.add("Ndian");

        arrayList_west = new ArrayList<>();
        arrayList_west.add("Bamboutos");
        arrayList_west.add("Haut-Nkam");
        arrayList_west.add("Hauts-Plateaux");
        arrayList_west.add("Koung-Khi");
        arrayList_west.add("Menoua");
        arrayList_west.add("Mifi");
        arrayList_west.add("Ndé");
        arrayList_west.add("Noun");

        region_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    arrayAdapter_division = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayList_adamawa);
                }
                if (i == 1){
                    arrayAdapter_division = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayList_far_north);
                }
                if (i == 2){
                    arrayAdapter_division = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayList_north);
                }
                if (i == 3){
                    arrayAdapter_division = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayList_centre);
                }
                if (i == 4){
                    arrayAdapter_division = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayList_east);
                }
                if (i == 5){
                    arrayAdapter_division = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayList_south);
                }
                if (i == 6){
                    arrayAdapter_division = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayList_littoral);
                }
                if (i == 7){
                    arrayAdapter_division = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayList_north_west);
                }
                if (i == 8){
                    arrayAdapter_division = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayList_south_west);
                }
                if (i == 9){
                    arrayAdapter_division = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayList_west);
                }

                division_sp.setAdapter(arrayAdapter_division);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void goToNextScreen(View view){

        if (!validateQuater()){
            return;
        }

        //get data
        String _category = getIntent().getStringExtra("category");
        String region_data = region_sp.getSelectedItem().toString();
        String division_data = division_sp.getSelectedItem().toString();
        String _quater = Objects.requireNonNull(quater.getEditText()).getText().toString().trim();

        if (_category.equals("Apartment") ){
            Intent intent = new Intent(getApplicationContext(), CreateHouseApartment.class);
            intent.putExtra("category", _category);
            intent.putExtra("region", region_data);
            intent.putExtra("division", division_data);
            intent.putExtra("quater", _quater);
            startActivity(intent);
            finish();
        }
        if (_category.equals("Guest House") ){
            Intent intent = new Intent(getApplicationContext(), CreateHouseApartment.class);
            intent.putExtra("category", _category);
            intent.putExtra("region", region_data);
            intent.putExtra("division", division_data);
            intent.putExtra("quater", _quater);
            startActivity(intent);
            finish();
        }
        if (_category.equals("Plot") ){
            Intent intent = new Intent(getApplicationContext(), CreatePlot1.class);
            intent.putExtra("category", _category);
            intent.putExtra("region", region_data);
            intent.putExtra("division", division_data);
            intent.putExtra("quater", _quater);
            startActivity(intent);
            finish();
        }
        if (_category.equals("Business Place") ){
            Intent intent = new Intent(getApplicationContext(), CreateHouseApartment.class);
            intent.putExtra("category", _category);
            intent.putExtra("region", region_data);
            intent.putExtra("division", division_data);
            intent.putExtra("quater", _quater);
            startActivity(intent);
            finish();
        }

    }

    private boolean validateQuater() {

        String val = Objects.requireNonNull(quater.getEditText()).getText().toString().trim();

        if (val.isEmpty()) {
            quater.setError("Field cannot be empty");
            return false;
        } else {
            quater.setError(null);
            quater.setErrorEnabled(false);
            return true;
        }

    }

    public void goBack(View view) {
        startActivity(new Intent(getApplicationContext(), CreateItem1.class));
    }
}