package com.uz.instaclonejava.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;
import com.uz.instaclonejava.R;
import com.uz.instaclonejava.manager.AuthManager;
import com.uz.instaclonejava.manager.DBManager;
import com.uz.instaclonejava.manager.StorageManager;
import com.uz.instaclonejava.manager.handler.DBPostHandler;
import com.uz.instaclonejava.manager.handler.DBUserHandler;
import com.uz.instaclonejava.manager.handler.StorageHandler;
import com.uz.instaclonejava.model.Post;
import com.uz.instaclonejava.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    EditText et_caption;
    Uri pickedPhoto;
    ArrayList<Uri> allPhoto = new ArrayList<>();
    UploadListener listener;

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
        et_caption = view.findViewById(R.id.et_caption);

        setViewHeight(fl_view);

        iv_close.setOnClickListener(view1 -> hidePickedPhoto());
        iv_pick.setOnClickListener(view13 -> pickFishBunPhoto());
        iv_upload.setOnClickListener(view12 -> uploadNewPost());
    }

    private void uploadNewPost() {
        String caption = et_caption.getText().toString().trim();
        if (!caption.isEmpty() && pickedPhoto != null) {
            uploadPostPhoto(caption,pickedPhoto);
        }
    }

    private void pickFishBunPhoto() {
        FishBun.with(this)
                .setImageAdapter(new GlideAdapter())
                .setMaxCount(1)
                .setMinCount(1)
                .setSelectedImages(allPhoto)
                .startAlbum();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FishBun.FISHBUN_REQUEST_CODE) {
            if (data != null) {
                allPhoto = data.getParcelableArrayListExtra(FishBun.INTENT_PATH);
            }
            pickedPhoto = allPhoto.get(0);
            showPickedPhoto();
        }
    }

    private void showPickedPhoto() {
        fl_photo.setVisibility(View.VISIBLE);
        iv_photo.setImageURI(pickedPhoto);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof UploadListener) {
            listener = (UploadListener) context;
        } else {
            throw new RuntimeException("$context must implement FirstListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface UploadListener {
        void scrollToHome();
    }

    private void hidePickedPhoto() {
        pickedPhoto = null;
        allPhoto.clear();
        fl_photo.setVisibility(View.GONE);
    }

    private void setViewHeight(FrameLayout view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = requireActivity().getWindowManager();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        params.height = displayMetrics.widthPixels;
        view.getLayoutParams();
    }

    public String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy hh:mm");
        return sdf.format(new Date());
    }

    private void uploadPostPhoto(String caption, Uri pickedPhoto) {
        showLoading(requireActivity());
        StorageManager.uploadPostPhoto(pickedPhoto, new StorageHandler() {
            @Override
            public void onSuccess(String imgUrl) {
                Post post = new Post(caption, imgUrl);
                post.setCurrentDate(getCurrentTime());
                String uid = AuthManager.currentUser().getUid();
                DBManager.loadUser(uid, new DBUserHandler() {
                    @Override
                    public void onSuccess(User user) {
                        post.setUid(uid);
                        post.setFullname(user.getFullname());
                        post.setUserImg(user.getUserImg());
                        storePostToDB(post);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

            }

            @Override
            public void onError(Exception e) {

            }
        });

    }

    private void storePostToDB(Post post) {
        DBManager.storePosts(post, new DBPostHandler() {
            @Override
            public void onSuccess(Post post) {
                storeFeedToDB(post);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void storeFeedToDB(Post post) {
        DBManager.storeFeeds(post, new DBPostHandler() {
            @Override
            public void onSuccess(Post post) {
                dissmisLoading();
                resetAll();
                listener.scrollToHome();
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void resetAll() {
        allPhoto.clear();
        et_caption.getText().clear();
        pickedPhoto = null;
        fl_photo.setVisibility(View.GONE);
    }
}