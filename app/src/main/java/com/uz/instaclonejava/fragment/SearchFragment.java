package com.uz.instaclonejava.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uz.instaclonejava.R;
import com.uz.instaclonejava.adapter.SearchAdapter;
import com.uz.instaclonejava.model.User;

import java.util.ArrayList;

/**
 * This is the Search page, where you can search
 */
public class SearchFragment extends BaseFragment {
    RecyclerView rv_search;
    EditText et_search;
    ArrayList<User> items = new ArrayList<>();
    ArrayList<User> users = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_search, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        rv_search = view.findViewById(R.id.rv_search);
        rv_search.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        et_search = view.findViewById(R.id.et_search);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                String keyword = s.toString().trim();
                usersByKeyword(keyword);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        refreshAdapter(loadUsers());
    }

    private ArrayList<User> loadUsers() {
        items = new ArrayList<User>();
        items.add(new User("Eldor", "eldor@gmail.com"));
        items.add(new User("Farrux", "eldor@gmail.com"));
        items.add(new User("Shoxrux", "eldor@gmail.com"));
        items.add(new User("Elyor", "eldor@gmail.com"));
        items.add(new User("Eldor", "eldor@gmail.com"));
        items.add(new User("Farrux", "eldor@gmail.com"));
        items.add(new User("Shoxrux", "eldor@gmail.com"));
        items.add(new User("Elyor", "eldor@gmail.com"));
        items.add(new User("Eldor", "eldor@gmail.com"));
        items.add(new User("Farrux", "eldor@gmail.com"));
        items.add(new User("Shoxrux", "eldor@gmail.com"));
        items.add(new User("Elyor", "eldor@gmail.com"));
        items.add(new User("Eldor", "eldor@gmail.com"));
        items.add(new User("Farrux", "eldor@gmail.com"));
        items.add(new User("Shoxrux", "eldor@gmail.com"));
        items.add(new User("Elyor", "eldor@gmail.com"));
        items.add(new User("Eldor", "eldor@gmail.com"));
        items.add(new User("Farrux", "eldor@gmail.com"));
        items.add(new User("Shoxrux", "eldor@gmail.com"));
        items.add(new User("Elyor", "eldor@gmail.com"));
        items.add(new User("Eldor", "eldor@gmail.com"));
        items.add(new User("Farrux", "eldor@gmail.com"));
        items.add(new User("Shoxrux", "eldor@gmail.com"));
        items.add(new User("Elyor", "eldor@gmail.com"));
        return items;
    }

    private void usersByKeyword(String keyword) {
        if (keyword.isEmpty())
            refreshAdapter(items);
        users.clear();
        for (User user : items)
            if (user.getFullName().toLowerCase().startsWith(keyword.toLowerCase()))
                users.add(user);

        refreshAdapter(users);

    }

    private void refreshAdapter(ArrayList<User> items) {
        SearchAdapter adapter = new SearchAdapter(this, items);
        rv_search.setAdapter(adapter);
    }
}