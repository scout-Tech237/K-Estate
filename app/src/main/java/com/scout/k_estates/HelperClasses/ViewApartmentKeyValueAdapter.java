package com.scout.k_estates.HelperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.scout.k_estates.R;

import java.util.ArrayList;

public class ViewApartmentKeyValueAdapter extends ArrayAdapter<ViewApartmentKeyValue> {

    private Context mContext;
    int mResource;

    public ViewApartmentKeyValueAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ViewApartmentKeyValue> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //get the info
        String key = getItem(position).getKey();
        String value = getItem(position).getValue();

        //create a list item with the object
        ViewApartmentKeyValue viewApartmentKeyValue = new ViewApartmentKeyValue(key,value);

        //create and inflate linear layout inflater
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvKey = convertView.findViewById(R.id.key);
        TextView tvValue = convertView.findViewById(R.id.value);

        //set text
        tvKey.setText(key);
        tvValue.setText(value);

        return convertView;
    }
}
