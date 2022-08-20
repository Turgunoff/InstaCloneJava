package com.uz.instaclonejava.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.uz.instaclonejava.R;
import com.uz.instaclonejava.fragment.HomeFragment;
import com.uz.instaclonejava.manager.AuthManager;
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
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Post data = items.get(position);
        if (holder instanceof PostViewHolder) {
            ((PostViewHolder) holder).tv_fullname.setText(data.getFullname());
            ((PostViewHolder) holder).tv_caption.setText(data.getCaption());
            ((PostViewHolder) holder).tv_time.setText(data.getCurrentDate());
            ((PostViewHolder) holder).iv_like.setOnClickListener(view -> {
                if (data.isLiked()) {
                    data.setLiked(false);
                    ((PostViewHolder) holder).iv_like.setImageResource(R.mipmap.ic_favorite);
                } else {
                    data.setLiked(true);
                    ((PostViewHolder) holder).iv_like.setImageResource(R.mipmap.ic_favorite_like);
                }
                fragment.likeOrUnlikePost(data);
            });
            if (data.isLiked()) {
                ((PostViewHolder) holder).iv_like.setImageResource(R.mipmap.ic_favorite_like);
            } else {
                ((PostViewHolder) holder).iv_like.setImageResource(R.mipmap.ic_favorite);
            }

            Glide.with(fragment).load(data.getUserImg()).placeholder(R.drawable.ic_person)
                    .error(R.drawable.ic_person).into(((PostViewHolder) holder).iv_profile);
            Glide.with(fragment).load(data.getPostImg()).into(((PostViewHolder) holder).iv_post);
            String uid = AuthManager.currentUser().getUid();
            if (uid.equals(data.getUid())) {
                ((PostViewHolder) holder).iv_more.setVisibility(View.VISIBLE);
            } else {
                ((PostViewHolder) holder).iv_more.setVisibility(View.GONE);
            }
            ((PostViewHolder) holder).iv_more.setOnClickListener(view -> fragment.showDeleteDialog(data));
        }
    }

    private static class PostViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView iv_profile;
        ShapeableImageView iv_post;
        TextView tv_fullname;
        TextView tv_time;
        TextView tv_caption;
        ImageView iv_more;
        ImageView iv_like;
        ImageView iv_share;

        public PostViewHolder(View view) {
            super(view);
            iv_post = view.findViewById(R.id.iv_post);
            iv_profile = view.findViewById(R.id.iv_profile);
            tv_fullname = view.findViewById(R.id.tv_fullname);
            tv_time = view.findViewById(R.id.tv_time);
            tv_caption = view.findViewById(R.id.tv_caption);
            iv_more = view.findViewById(R.id.iv_more);
            iv_like = view.findViewById(R.id.iv_like);
            iv_share = view.findViewById(R.id.iv_share);
        }
    }
}
