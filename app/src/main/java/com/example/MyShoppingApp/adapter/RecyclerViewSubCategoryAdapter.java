package com.example.MyShoppingApp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.MyShoppingApp.interfaces.RecyclerViewClickListener;
import com.example.myshoppingapp.R;

import java.util.List;

public class RecyclerViewSubCategoryAdapter extends RecyclerView.Adapter<RecyclerViewSubCategoryAdapter.ViewHolder> {
    List<String> titles ;
    List<String> images;
    Context context;
    LayoutInflater layoutInflater;
    private static RecyclerViewClickListener itemListener;

    public RecyclerViewSubCategoryAdapter(List<String> titles, List<String> images, Context context,RecyclerViewClickListener itemListener) {
        this.titles = titles;
        this.images = images;
        this.context = context;
        this.itemListener=itemListener;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public RecyclerViewSubCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = layoutInflater.inflate(R.layout.subcategory_layout, parent , false);
        return new RecyclerViewSubCategoryAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewSubCategoryAdapter.ViewHolder holder, int position) {
        holder.title.setText(titles.get(position));
        Glide.with(context).load(images.get(position)).into(holder.gridIcon);
    }
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        ImageView gridIcon;
        TextView title;
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gridIcon = itemView.findViewById(R.id.subcategory_image);
            title = itemView.findViewById(R.id.subcategory_text);
            constraintLayout = itemView.findViewById(R.id.subcategory_constraint_layout);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, this.getLayoutPosition());

        }
    }



}
