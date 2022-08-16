package com.uz.instaclonejava.manager;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.uz.instaclonejava.manager.handler.DBUserHandler;
import com.uz.instaclonejava.manager.handler.DBUsersHandler;
import com.uz.instaclonejava.model.User;

import java.util.ArrayList;

/**
 * Created by Eldor Turgunov on 16.08.2022.
 * Insta Clone Java
 * eldorturgunov777@gmail.com
 */


public class DBManager {
    private final static String USER_PATH = "users";
    private final static String POST_PATH = "posts";
    private final static String FEED_PATH = "feeds";
    private final static String FOLLOWING_PATH = "following";
    private final static String FOLLOWERS_PATH = "followers";
    private final static FirebaseFirestore database = FirebaseFirestore.getInstance();

    public static void storeUser(User user, DBUserHandler handler) {
        database.collection(USER_PATH).document(user.getUid()).set(user).addOnSuccessListener(unused ->
                handler.onSuccess(user)
        ).addOnFailureListener(
                handler::onError
        );
    }

    public static void updateUserImg(String userImg) {
        String uid = AuthManager.currentUser().getUid();
        database.collection(USER_PATH).document(uid).update("userImg", userImg);
    }

    public static void loadUser(String uid, DBUserHandler handler) {
        database.collection(USER_PATH).document(uid).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String fullname = documentSnapshot.getString("fullname");
                String email = documentSnapshot.getString("email");
                String userImg = documentSnapshot.getString("userImg");

                User user = new User(fullname, email, userImg);
                user.setUid(uid);
                handler.onSuccess(user);
            } else {
                handler.onSuccess(null);
            }
        }).addOnFailureListener(handler::onError);
    }

    public static void loadUsers(DBUsersHandler handler) {
        ArrayList<User> users = new ArrayList<>();
        database.collection(USER_PATH).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String uid = document.getString("uid");
                    String fullname = document.getString("fullname");
                    String email = document.getString("email");
                    String userImg = document.getString("userImg");
                    User user = new User(fullname, email, userImg);
                    user.setUid(uid);
                    users.add(user);
                }
                handler.onSuccess(users);
            } else {
                handler.onError(task.getException());
            }
        });
    }
}

