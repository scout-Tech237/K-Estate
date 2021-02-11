package com.scout.k_estates.HelperClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scout.k_estates.R;

import java.util.ArrayList;

public class CategoryRecyclerviewAdapter extends RecyclerView.Adapter<CategoryRecyclerviewAdapter.CategoryViewHolder> {

    ArrayList<CategoryHelperClass> categoies;
    RecyclerviewClickListener listener;

    public CategoryRecyclerviewAdapter(ArrayList<CategoryHelperClass> categoies, RecyclerviewClickListener listener) {
        this.categoies = categoies;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_userdashboard_cardview, parent, false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        CategoryHelperClass categoryHelperClass = categoies.get(position);
        holder.image.setImageResource(categoryHelperClass.getImage());
        holder.title.setText(categoryHelperClass.getTitle());
    }

    @Override
    public int getItemCount() {
        return categoies.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView image;
        TextView title;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            //hooks
            image = itemView.findViewById(R.id.category_image);
            title = itemView.findViewById(R.id.category_title);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    public interface RecyclerviewClickListener{
        void onClick(View v, int position);
    }
}
