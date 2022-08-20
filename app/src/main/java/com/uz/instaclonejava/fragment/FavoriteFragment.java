package com.uz.instaclonejava.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uz.instaclonejava.R;
import com.uz.instaclonejava.adapter.FavoriteAdapter;
import com.uz.instaclonejava.adapter.HomeAdapter;
import com.uz.instaclonejava.manager.AuthManager;
import com.uz.instaclonejava.manager.DBManager;
import com.uz.instaclonejava.manager.handler.DBPostHandler;
import com.uz.instaclonejava.manager.handler.DBPostsHandler;
import com.uz.instaclonejava.model.Post;
import com.uz.instaclonejava.utils.Utils;

import java.util.ArrayList;

/**
 * This is the Favorite page. Here you can see all the followers and likes
 */
public class FavoriteFragment extends BaseFragment {
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_favorite, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        loadLikedFeeds();
    }

    private void loadLikedFeeds() {
        showLoading(requireActivity());
        String uid = AuthManager.currentUser().getUid();
        DBManager.loadLikedFeeds(uid, new DBPostsHandler() {

            @Override
            public void onSuccess(ArrayList<Post> posts) {
                dismissLoading();
                refreshAdapter(posts);
            }

            @Override
            public void onError(Exception e) {
                dismissLoading();
            }
        });
    }

    private void refreshAdapter(ArrayList<Post> items) {
        FavoriteAdapter adapter = new FavoriteAdapter(this, items);
        recyclerView.setAdapter(adapter);
    }

    public void likeOrUnlikePost(Post data) {
        String uid = AuthManager.currentUser().getUid();
        DBManager.likeFeedPost(uid, data);

        loadLikedFeeds();
    }

    public void showDeleteDialog(Post post) {
        Utils.dialogDouble(requireContext(), getString(R.string.str_detele_post), isChosen -> {
            if (isChosen)
                deletePost(post);
        });
    }

    private void deletePost(Post post) {
        DBManager.deletePost(post, new DBPostHandler() {
            @Override
            public void onSuccess(Post post) {
                loadLikedFeeds();
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}