package com.uz.instaclonejava.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uz.instaclonejava.R;
import com.uz.instaclonejava.adapter.HomeAdapter;
import com.uz.instaclonejava.manager.AuthManager;
import com.uz.instaclonejava.manager.DBManager;
import com.uz.instaclonejava.manager.handler.DBPostsHandler;
import com.uz.instaclonejava.model.Post;

import java.util.ArrayList;

/**
 * This is the Home Page, where you and your friends will see posts
 */
public class HomeFragment extends BaseFragment {
    RecyclerView recyclerView;
    HomeListener listener;
    ImageView iv_camera;
    ArrayList<Post> feeds = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        iv_camera = view.findViewById(R.id.iv_camera);
        iv_camera.setOnClickListener(view1 -> listener.scrollToUpload());
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        loadMyFeeds();
    }

    private void loadMyFeeds() {
        showLoading(requireActivity());
        String uid = AuthManager.currentUser().getUid();
        DBManager.loadFeeds(uid, new DBPostsHandler() {
            @Override
            public void onSuccess(ArrayList<Post> post) {
                dismissLoading();
                feeds.clear();
                feeds.addAll(post);
                refreshAdapter(feeds);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void refreshAdapter(ArrayList<Post> items) {
        HomeAdapter adapter = new HomeAdapter(this, items);
        recyclerView.setAdapter(adapter);
    }

    public void likeOrUnlikePost(Post data) {
        String uid = AuthManager.currentUser().getUid();
        DBManager.likeFeedPost(uid, data);
    }

    public interface HomeListener {
        void scrollToUpload();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof HomeListener) {
            listener = (HomeListener) context;
        } else {
            throw new RuntimeException("$context must implement FirstListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}