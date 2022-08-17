package com.uz.instaclonejava.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;
import com.uz.instaclonejava.R;
import com.uz.instaclonejava.activity.LogInActivity;
import com.uz.instaclonejava.adapter.ProfileAdapter;
import com.uz.instaclonejava.manager.AuthManager;
import com.uz.instaclonejava.manager.DBManager;
import com.uz.instaclonejava.manager.StorageManager;
import com.uz.instaclonejava.manager.handler.DBPostsHandler;
import com.uz.instaclonejava.manager.handler.DBUserHandler;
import com.uz.instaclonejava.manager.handler.StorageHandler;
import com.uz.instaclonejava.model.Post;
import com.uz.instaclonejava.model.User;

import java.util.ArrayList;

/**
 * This is the Profile page, where profile settings can be made
 */
public class ProfileFragment extends BaseFragment {
    String TAG = ProfileFragment.class.toString();
    RecyclerView rv_profile;
    ImageView logOut;
    ArrayList<Uri> allPhotos = new ArrayList<>();
    Uri pickedPhoto;
    ImageView iv_profile;
    TextView tv_fullname;
    TextView tv_email;
    TextView tv_posts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_profile, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        rv_profile = view.findViewById(R.id.rv_profile);
        logOut = view.findViewById(R.id.logOut);
        iv_profile = view.findViewById(R.id.iv_profile);
        tv_fullname = view.findViewById(R.id.tv_fullname);
        tv_email = view.findViewById(R.id.tv_email);
        tv_posts = view.findViewById(R.id.tv_posts);

        logOut.setOnClickListener(view12 -> {
            AuthManager.signOut();
            Intent intent = new Intent(getActivity(), LogInActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });


        rv_profile.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ShapeableImageView iv_profile = view.findViewById(R.id.iv_profile);
        iv_profile.setOnClickListener(view1 -> pickFishBunPhoto());

        loadUserInfo();
        loadMyPosts();
    }

    private void loadMyPosts() {
        String uid = AuthManager.currentUser().getUid();
        DBManager.loadPosts(uid, new DBPostsHandler() {
            @Override
            public void onSuccess(ArrayList<Post> post) {
                tv_posts.setText(String.valueOf(post.size()));
                refreshAdapter(post);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void loadUserInfo() {
        DBManager.loadUser(AuthManager.currentUser().getUid(), new DBUserHandler() {
            @Override
            public void onSuccess(User user) {
                if (user != null) {
                    showUserInfo(user);
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void showUserInfo(User user) {
        tv_fullname.setText(user.getFullname());
        tv_email.setText(user.getEmail());

        Glide.with(this).load(user.getUserImg())
                .placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person)
                .into(iv_profile);
    }

    private void pickFishBunPhoto() {
        FishBun.with(this)
                .setImageAdapter(new GlideAdapter())
                .setMaxCount(1)
                .setMinCount(1)
                .setSelectedImages(allPhotos)
                .startAlbum();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FishBun.FISHBUN_REQUEST_CODE) {
            if (data != null) {
                allPhotos = data.getParcelableArrayListExtra(FishBun.INTENT_PATH);
            }
            pickedPhoto = allPhotos.get(0);
            uploadUserPhoto();
        }
    }

    private void uploadUserPhoto() {
        if (pickedPhoto == null) return;

        StorageManager.uploadUserPhoto(pickedPhoto, new StorageHandler() {
            @Override
            public void onSuccess(String imgUrl) {
                DBManager.updateUserImg(imgUrl);
                iv_profile.setImageURI(pickedPhoto);
            }

            @Override
            public void onError(Exception e) {

            }
        });

    }

    private void refreshAdapter(ArrayList<Post> items) {
        ProfileAdapter adapter = new ProfileAdapter(this, items);
        rv_profile.setAdapter(adapter);
    }
}