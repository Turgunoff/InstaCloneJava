package com.uz.instaclonejava.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.uz.instaclonejava.R;
import com.uz.instaclonejava.fragment.SearchFragment;
import com.uz.instaclonejava.model.User;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter {
    SearchFragment fragment;
    ArrayList<User> items;

    public SearchAdapter(SearchFragment fragment, ArrayList<User> items) {
        this.fragment = fragment;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_search, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        User data = items.get(position);
        if (holder instanceof UserViewHolder) {
            ((UserViewHolder) holder).tv_fullname.setText(data.getFullname());
            ((UserViewHolder) holder).tv_email.setText(data.getEmail());
            Glide.with(fragment).load(data.getUserImg())
                    .placeholder(R.drawable.ic_person)
                    .error(R.drawable.ic_person)
                    .into(((UserViewHolder) holder).iv_profile);
            ((UserViewHolder) holder).tv_follow.setOnClickListener(view -> {
                if (data.getFollowed()) {
                    ((UserViewHolder) holder).tv_follow.setText(R.string.str_following);
                    ((UserViewHolder) holder).tv_follow.setTextColor(Color.BLACK);
                    ((UserViewHolder) holder).tv_follow.setBackgroundResource(R.drawable.following_click);
                } else {
                    ((UserViewHolder) holder).tv_follow.setText(R.string.str_follow);
                    ((UserViewHolder) holder).tv_follow.setTextColor(Color.WHITE);
                    ((UserViewHolder) holder).tv_follow.setBackgroundResource(R.drawable.textview_rounder_corners);
                }
                fragment.followOrUnFollow(data);
            });
            if (data.getFollowed()){
                ((UserViewHolder) holder).tv_follow.setText(R.string.str_follow);
                ((UserViewHolder) holder).tv_follow.setTextColor(Color.WHITE);
                ((UserViewHolder) holder).tv_follow.setBackgroundResource(R.drawable.textview_rounder_corners);
            }else{
                ((UserViewHolder) holder).tv_follow.setText(R.string.str_following);
                ((UserViewHolder) holder).tv_follow.setTextColor(Color.BLACK);
                ((UserViewHolder) holder).tv_follow.setBackgroundResource(R.drawable.following_click);
            }

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private static class UserViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView iv_profile;
        TextView tv_fullname;
        TextView tv_email;
        TextView tv_follow;

        public UserViewHolder(View view) {
            super(view);
            iv_profile = view.findViewById(R.id.iv_profile);
            tv_fullname = view.findViewById(R.id.tv_fullname);
            tv_email = view.findViewById(R.id.tv_email);
            tv_follow = view.findViewById(R.id.tv_follow);
        }
    }
}
