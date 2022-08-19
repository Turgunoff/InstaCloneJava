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
import com.uz.instaclonejava.manager.AuthManager;
import com.uz.instaclonejava.manager.DBManager;
import com.uz.instaclonejava.manager.handler.DBFollowHandler;
import com.uz.instaclonejava.manager.handler.DBUserHandler;
import com.uz.instaclonejava.manager.handler.DBUsersHandler;
import com.uz.instaclonejava.model.User;

import java.util.ArrayList;
import java.util.Collection;

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

        loadUsers();
    }

    private void loadUsers() {
        String uid = AuthManager.currentUser().getUid();
        DBManager.loadUsers(new DBUsersHandler() {
            @Override
            public void onSuccess(ArrayList<User> users) {
                DBManager.loadFollowing(uid, new DBUsersHandler() {
                    @Override
                    public void onSuccess(ArrayList<User> following) {
                        items.clear();
                        items.addAll(mergedUsers(uid, users, following));
                        refreshAdapter(items);
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

    private Collection<? extends User> mergedUsers(String uid, ArrayList<User> users, ArrayList<User> following) {
        ArrayList<User> items = new ArrayList<>();
        for (User u : users) {
            for (User f : following) {
                if (u.getUid().equals(f.getUid())) {
                    u.setFollowed(true);
                    break;
                }
            }
            if (!uid.equals(u.getUid())) {
                items.add(u);
            }
        }
        return items;
    }

    private void usersByKeyword(String keyword) {
        if (keyword.isEmpty())
            refreshAdapter(items);
        users.clear();
        for (User user : items)
            if (user.getFullname().toLowerCase().startsWith(keyword.toLowerCase()))
                users.add(user);

        refreshAdapter(users);

    }

    private void refreshAdapter(ArrayList<User> items) {
        SearchAdapter adapter = new SearchAdapter(this, items);
        rv_search.setAdapter(adapter);
    }

    public void followOrUnFollow(User to) {
        String uid = AuthManager.currentUser().getUid();
        if (!to.getFollowed()) {
            followUser(uid, to);
        } else {
            unFollowUser(uid, to);
        }
    }

    private void unFollowUser(String uid, User to) {
        DBManager.loadUser(uid, new DBUserHandler() {
            @Override
            public void onSuccess(User me) {
                DBManager.unFollowUser(me, to, new DBFollowHandler() {
                    @Override
                    public void onSuccess(Boolean isDone) {
                        to.setFollowed(false);
                        DBManager.removePostsFromMyFeed(uid, to);
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

    private void followUser(String uid, User to) {
        DBManager.loadUser(uid, new DBUserHandler() {
            @Override
            public void onSuccess(User me) {
                DBManager.followUser(me, to, new DBFollowHandler() {
                    @Override
                    public void onSuccess(Boolean isDone) {
                        to.setFollowed(true);
                        DBManager.storePostsToMyFeed(uid, to);
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
}