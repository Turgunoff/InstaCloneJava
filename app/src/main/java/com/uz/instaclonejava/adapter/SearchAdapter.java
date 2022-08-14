package com.uz.instaclonejava.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.uz.instaclonejava.R;
import com.uz.instaclonejava.fragment.HomeFragment;
import com.uz.instaclonejava.fragment.SearchFragment;
import com.uz.instaclonejava.model.Post;
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
            ((UserViewHolder) holder).tv_fullname.setText(data.getFullName());
            ((UserViewHolder) holder).tv_email.setText(data.getEmail());
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
