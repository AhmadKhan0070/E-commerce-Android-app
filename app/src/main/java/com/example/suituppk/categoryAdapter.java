package com.example.suituppk;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.ViewHolder> {

    private List<category_model> category_models_list;
    private int lastPosition = -1;


    public categoryAdapter(List<category_model> category_models_list) {
        this.category_models_list = category_models_list;
    }

    @NonNull
    @Override
    public categoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull categoryAdapter.ViewHolder viewHolder, int position) {

        String icon = category_models_list.get(position).getCategoryiconlink();
        String name = category_models_list.get(position).getCategoryName();
        viewHolder.setCategory(name,position);
        viewHolder.setCategoryIcon(icon);

        if (lastPosition < position) {
            Animation animation = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), R.anim.fade_in);
            viewHolder.itemView.setAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return category_models_list.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView categoryicon;
        private TextView categoryname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryicon = itemView.findViewById(R.id.category_icon);
            categoryname = itemView.findViewById(R.id.category_name);
        }

        private void setCategoryIcon(String iconUrl) {

            if (!iconUrl.equals("null")) {
                Glide.with(itemView.getContext()).load(iconUrl).apply(new RequestOptions().placeholder(R.drawable.nooo)).into(categoryicon);
            } else {
                categoryicon.setImageResource(R.drawable.ic_baseline_home_24);
            }
        }

        private void setCategory(final String name , final int position) {
            categoryname.setText(name);

            if (!name.equals(""))

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position != 0) {
                            Intent categoryIntent = new Intent(itemView.getContext(), CategoryActivity.class);
                            categoryIntent.putExtra("CategoryName", name);
                            itemView.getContext().startActivity(categoryIntent);
                        }
                    }
                });
        }
        }

    }


