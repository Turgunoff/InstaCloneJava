package com.uz.instaclonejava.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;
import com.uz.instaclonejava.R;
import com.uz.instaclonejava.activity.LogInActivity;
import com.uz.instaclonejava.adapter.ProfileAdapter;
import com.uz.instaclonejava.manager.AuthManager;
import com.uz.instaclonejava.model.Post;

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

        logOut.setOnClickListener(view12 -> {
            AuthManager.signOut();
            Intent intent = new Intent(getActivity(), LogInActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });


        rv_profile.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ShapeableImageView iv_profile = view.findViewById(R.id.iv_profile);
        iv_profile.setOnClickListener(view1 -> pickFishBunPhoto());
        refreshAdapter(loadPosts());
    }

    private ArrayList<Post> loadPosts() {
        ArrayList<Post> items = new ArrayList<>();
        items.add(new Post("https://images.unsplash.com/photo-1659519529276-a6a42aaa0b7b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDEyfENEd3V3WEpBYkV3fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        items.add(new Post("https://images.unsplash.com/photo-1659259541374-22a6df2fee1d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDIxfENEd3V3WEpBYkV3fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        items.add(new Post("https://images.unsplash.com/photo-1544005313-94ddf0286df2?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8cGVyc29ufGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        items.add(new Post("https://images.unsplash.com/photo-1492681290082-e932832941e6?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTJ8fHBlcnNvbnxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        items.add(new Post("https://images.unsplash.com/photo-1659519529276-a6a42aaa0b7b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDEyfENEd3V3WEpBYkV3fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        items.add(new Post("https://images.unsplash.com/photo-1659259541374-22a6df2fee1d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDIxfENEd3V3WEpBYkV3fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        items.add(new Post("https://images.unsplash.com/photo-1544005313-94ddf0286df2?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8cGVyc29ufGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        items.add(new Post("https://images.unsplash.com/photo-1492681290082-e932832941e6?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTJ8fHBlcnNvbnxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        return items;
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
            showPickedPhoto();
        }
    }

    private void showPickedPhoto() {
        if (pickedPhoto != null) {
            Log.d(TAG, pickedPhoto.getPath());
        }
    }

    private void refreshAdapter(ArrayList<Post> items) {
        ProfileAdapter adapter = new ProfileAdapter(this, items);
        rv_profile.setAdapter(adapter);
    }
}