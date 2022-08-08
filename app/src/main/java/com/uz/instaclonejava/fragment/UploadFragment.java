package com.uz.instaclonejava.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;
import com.uz.instaclonejava.R;
import com.uz.instaclonejava.utils.Utils;

import java.util.ArrayList;

/**
 * This is the Upload page, where you can upload images and videos to your page
 */

public class UploadFragment extends BaseFragment {
    ImageView iv_close;
    ImageView iv_pick;
    ImageView iv_upload;
    ImageView iv_photo;
    FrameLayout fl_view;
    FrameLayout fl_photo;
    Uri pickedPhoto;
    ArrayList<Uri> allPhoto = new ArrayList<>();

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        iv_close = view.findViewById(R.id.iv_close);
        iv_pick = view.findViewById(R.id.iv_pick);
        iv_upload = view.findViewById(R.id.iv_upload);
        fl_view = view.findViewById(R.id.fl_view);
        fl_photo = view.findViewById(R.id.fl_photo);
        iv_photo = view.findViewById(R.id.iv_photo);

        setViewHeight(fl_view);

        iv_close.setOnClickListener(view1 -> hidePickedPhoto());
        iv_pick.setOnClickListener(view13 -> pickFishBunPhoto());
        iv_upload.setOnClickListener(view12 -> uploadNewPost());
    }

    private void uploadNewPost() {
    }

    private void pickFishBunPhoto() {
        FishBun.with(UploadFragment.this)
                .setImageAdapter(new GlideAdapter())
                .setMaxCount(1)
                .setMinCount(1)
                .setSelectedImages(allPhoto);
//                .startAlbumWithOnActivityResult(photoLauncher);
    }

    private void hidePickedPhoto() {
        pickedPhoto = null;
        allPhoto.clear();
        fl_photo.setVisibility(View.GONE);
    }

    private void setViewHeight(FrameLayout view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
//        params.height = Utils.screenSize(requireActivity().getApplication());
        view.getLayoutParams();
    }
}