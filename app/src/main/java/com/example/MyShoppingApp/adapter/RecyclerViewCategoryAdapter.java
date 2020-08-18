package com.example.MyShoppingApp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.MyShoppingApp.interfaces.RecyclerViewClickListener;
import com.example.myshoppingapp.R;

import java.util.List;

public class RecyclerViewCategoryAdapter extends RecyclerView.Adapter<RecyclerViewCategoryAdapter.ViewHolder> {

    List<String> titles;
    List<String> images;
    Context context;
    LayoutInflater layoutInflater;
    private static RecyclerViewClickListener itemListener;
    public RecyclerViewCategoryAdapter(List<String> titles, List<String> images, Context context,RecyclerViewClickListener itemListener) {
        this.titles = titles;
        this.images = images;
        this.context = context;
        this.itemListener = itemListener;
        layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = layoutInflater.inflate(R.layout.category_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.title.setText(titles.get(position));
        Glide.with(context).load(images.get(position)).into(holder.gridIcon);
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView gridIcon;
        TextView title;
        ConstraintLayout constraintLayout;
        CardView cardView;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            gridIcon = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.textView);
            constraintLayout = itemView.findViewById(R.id.category_constraint_layout);
            cardView = itemView.findViewById(R.id.category_card_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }
    }
}
