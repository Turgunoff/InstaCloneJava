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
import com.uz.instaclonejava.model.Post;

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
        refreshAdapter();
    }

    private void refreshAdapter() {
        ArrayList<Post> items = new ArrayList<>();
        FavoriteAdapter adapter = new FavoriteAdapter(this, items);
        recyclerView.setAdapter(adapter);
        items.add(new Post("https://images.unsplash.com/photo-1659519529276-a6a42aaa0b7b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDEyfENEd3V3WEpBYkV3fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        items.add(new Post("https://images.unsplash.com/photo-1659259541374-22a6df2fee1d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDIxfENEd3V3WEpBYkV3fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        items.add(new Post("https://images.unsplash.com/photo-1544005313-94ddf0286df2?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8cGVyc29ufGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        items.add(new Post("https://images.unsplash.com/photo-1492681290082-e932832941e6?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTJ8fHBlcnNvbnxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
    }
}