package com.uz.instaclonejava.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.uz.instaclonejava.R;
import com.uz.instaclonejava.fragment.HomeFragment;
import com.uz.instaclonejava.model.Post;

import java.util.ArrayList;

public class HomeAdapter extends BaseAdapter {
    HomeFragment fragment;
    ArrayList<Post> items;

    public HomeAdapter(HomeFragment fragment, ArrayList<Post> items) {
        this.fragment = fragment;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_home, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Post data = items.get(position);
        if (holder instanceof PostViewHolder) {
            Glide.with(fragment).load(data.getImage()).into(((PostViewHolder) holder).iv_post);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private static class PostViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView iv_post;

        public PostViewHolder(View view) {
            super(view);
            iv_post = view.findViewById(R.id.iv_post);
        }
    }
}
