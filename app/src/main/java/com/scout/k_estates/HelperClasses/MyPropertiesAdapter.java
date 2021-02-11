package com.scout.k_estates.HelperClasses;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.scout.k_estates.LocationOwner.MyProperties;
import com.scout.k_estates.R;
import com.scout.k_estates.User.ViewItems.ViewApartment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MyPropertiesAdapter extends ArrayAdapter {

    private Activity mContext;
    List<MyPropertiesModel> myPropertiesModelList;
    List<MyPropertiesModel> myPropertiesModelFullList;

    public MyPropertiesAdapter(Activity mContext, List<MyPropertiesModel> myPropertiesModelList){

        super(mContext, R.layout.my_properties, myPropertiesModelList);
        this.mContext = mContext;
        this.myPropertiesModelList = myPropertiesModelList;
        myPropertiesModelFullList = new ArrayList<>(myPropertiesModelList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.my_properties, null, true);
        TextView tvName = listItemView.findViewById(R.id.my_prop_item_title);
        TextView tvDesc = listItemView.findViewById(R.id.my_prop_item_desc);
        TextView tvPrice = listItemView.findViewById(R.id.my_prop_item_price);
        ImageView imageView = listItemView.findViewById(R.id.my_prop_item_image);

        MyPropertiesModel myPropertiesModel = myPropertiesModelList.get(position);

        tvName.setText(myPropertiesModel.getPostTitle());
        tvDesc.setText(myPropertiesModel.getRegion() + ", " + myPropertiesModel.getNeighborhood());
        tvPrice.setText(myPropertiesModel.getPrice());
        tvDesc.setCompoundDrawablesWithIntrinsicBounds(R.drawable.location_icon, 0,0,0);

        String imageUri;
        imageUri = myPropertiesModel.getImage();
        Picasso.get().load(imageUri).into(imageView);

        //get other data
        String _postType = myPropertiesModel.getPostType();
        String _room = myPropertiesModel.getRooms();
        String _parlour = myPropertiesModel.getParlour();
        String _internalToilet = myPropertiesModel.getInternalToilet();
        String _price = myPropertiesModel.getPrice();
        String _caution = myPropertiesModel.getCaution();
        String _advancePayment = myPropertiesModel.getAdvancePayment();
        String _apartmentName = myPropertiesModel.getApartmentName();
        String _region = myPropertiesModel.getRegion();
        String _division = myPropertiesModel.getDivision();
        String _neighborhood = myPropertiesModel.getNeighborhood();
        String _externalToilet = myPropertiesModel.getExternalToilet();
        String _postedBy = myPropertiesModel.getPostedBy();
        String _phoneNumCall = myPropertiesModel.getPhoneNumCall();
        String _additionalInfo = myPropertiesModel.getAdditionalInfo();
        String _postTitle = myPropertiesModel.getPostTitle();
        String _category = myPropertiesModel.getCategory();
        String _lon = myPropertiesModel.getLon();
        String _lat = myPropertiesModel.getLat();
        String uid = myPropertiesModel.getUId();
        String activity = "MyProperty";
        String activity1 = "MyPropertyNo";

        //if userdashboard calls adapter

        if (mContext instanceof MyProperties){
            listItemView.setOnClickListener(view -> {


                Intent intent = new Intent(mContext.getApplicationContext(), ViewApartment.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);



                //send data to next activity
                intent.putExtra("postType", _postType);
                intent.putExtra("rooms", _room);
                intent.putExtra("parlour", _parlour);
                intent.putExtra("internalToilet", _internalToilet);
                intent.putExtra("price", _price);
                intent.putExtra("caution", _caution);
                intent.putExtra("advancePayment", _advancePayment);
                intent.putExtra("apartmentName", _apartmentName);
                intent.putExtra("region", _region);
                intent.putExtra("division", _division);
                intent.putExtra("quater", _neighborhood);
                intent.putExtra("externalToilet", _externalToilet);
                intent.putExtra("postedBy", _postedBy);
                intent.putExtra("phoneNumCall", _phoneNumCall);
                intent.putExtra("additionalInfo", _additionalInfo);
                intent.putExtra("postTitle", _postTitle);
                intent.putExtra("category", _category);
                intent.putExtra("longitude", _lon);
                intent.putExtra("latitude", _lat);
                intent.putExtra("ApartmentKey", uid);
                intent.putExtra("Activity", activity);
                //intent.putExtra("postedDate", postedDate);
                mContext.getApplicationContext().startActivity(intent);


            });
        } else {
            listItemView.setOnClickListener(view -> {


                Intent intent = new Intent(mContext.getApplicationContext(), ViewApartment.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);



                //send data to next activity
                intent.putExtra("postType", _postType);
                intent.putExtra("rooms", _room);
                intent.putExtra("parlour", _parlour);
                intent.putExtra("internalToilet", _internalToilet);
                intent.putExtra("price", _price);
                intent.putExtra("caution", _caution);
                intent.putExtra("advancePayment", _advancePayment);
                intent.putExtra("apartmentName", _apartmentName);
                intent.putExtra("region", _region);
                intent.putExtra("division", _division);
                intent.putExtra("quater", _neighborhood);
                intent.putExtra("externalToilet", _externalToilet);
                intent.putExtra("postedBy", _postedBy);
                intent.putExtra("phoneNumCall", _phoneNumCall);
                intent.putExtra("additionalInfo", _additionalInfo);
                intent.putExtra("postTitle", _postTitle);
                intent.putExtra("category", _category);
                intent.putExtra("longitude", _lon);
                intent.putExtra("latitude", _lat);
                intent.putExtra("ApartmentKey", uid);
                intent.putExtra("Activity", activity1);
                //intent.putExtra("postedDate", postedDate);
                mContext.getApplicationContext().startActivity(intent);

            });
        }

        return listItemView;

    }


}

