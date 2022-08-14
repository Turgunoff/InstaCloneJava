package com.uz.instaclonejava.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.uz.instaclonejava.R;
import com.uz.instaclonejava.fragment.ProfileFragment;
import com.uz.instaclonejava.model.Post;

import java.util.ArrayList;

public class ProfileAdapter extends BaseAdapter {
    ProfileFragment fragment;
    ArrayList<Post> items;
    Context context;

    public ProfileAdapter(ProfileFragment fragment, ArrayList<Post> items) {
        this.fragment = fragment;
        this.items = items;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Post post = items.get(position);
        if (holder instanceof PostViewHolder) {
            setViewHeight(((PostViewHolder) holder).iv_post);
            Glide.with(fragment).load(post.getImage()).into(((PostViewHolder) holder).iv_post);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_profile, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private static class PostViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView iv_post;
        TextView tv_caption;

        public PostViewHolder(View view) {
            super(view);
            iv_post = view.findViewById(R.id.iv_post);
            tv_caption = view.findViewById(R.id.tv_caption);
        }
    }

    private void setViewHeight(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = fragment.requireActivity().getWindowManager();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        params.height = displayMetrics.widthPixels/2;
        view.getLayoutParams();
    }
}
