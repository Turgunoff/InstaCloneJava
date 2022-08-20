package com.uz.instaclonejava.manager;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.uz.instaclonejava.manager.handler.DBFollowHandler;
import com.uz.instaclonejava.manager.handler.DBPostHandler;
import com.uz.instaclonejava.manager.handler.DBPostsHandler;
import com.uz.instaclonejava.manager.handler.DBUserHandler;
import com.uz.instaclonejava.manager.handler.DBUsersHandler;
import com.uz.instaclonejava.model.Post;
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

    public static void followUser(User me, User to, DBFollowHandler handler) {
        // User(To) is in my following
        database.collection(USER_PATH).document(me.getUid()).collection(FOLLOWING_PATH).document(to.getUid())
                .set(to).addOnSuccessListener(unused -> {
                    // User(Me) is in his/her followers
                    database.collection(USER_PATH).document(to.getUid()).collection(FOLLOWERS_PATH)
                            .document(me.getUid())
                            .set(me).addOnSuccessListener(unused1 ->
                                    handler.onSuccess(true)
                            ).addOnFailureListener(e -> handler.onError(e));
                }).addOnFailureListener(e -> handler.onError(e));
    }

    public static void loadFollowing(String uid, DBUsersHandler handler) {
        ArrayList<User> users = new ArrayList<>();
        database.collection(USER_PATH).document(uid).collection(FOLLOWING_PATH).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String uid1 = document.getString("uid");
                            String fullname = document.getString("fullname");
                            String email = document.getString("email");
                            String userImg = document.getString("userImg");
                            User user = new User(fullname, email, userImg);

                            user.setUid(uid1);
                            users.add(user);
                        }
                        handler.onSuccess(users);
                    } else {
                        handler.onError(task.getException());
                    }
                });
    }

    public static void loadFollowers(String uid, DBUsersHandler handler) {
        ArrayList<User> users = new ArrayList<>();
        database.collection(USER_PATH).document(uid).collection(FOLLOWERS_PATH).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()
                ) {
                    String uid1 = document.getString("uid");
                    String fullname = document.getString("fullname");
                    String email = document.getString("email");
                    String userImg = document.getString("userImg");
                    User user = new User(fullname, email, userImg);
                    user.setUid(uid1);
                    users.add(user);
                }
                handler.onSuccess(users);
            } else {
                handler.onError(task.getException());
            }
        });

    }

    public static void unFollowUser(User me, User to, DBFollowHandler handler) {
        database.collection(USER_PATH).document(me.getUid()).collection(FOLLOWING_PATH).document(to.getUid())
                .delete().addOnSuccessListener(unused -> {
                    database.collection(USER_PATH).document(to.getUid()).collection(FOLLOWERS_PATH)
                            .document(me.getUid())
                            .delete().addOnSuccessListener(unused1 ->
                                    handler.onSuccess(true)
                            ).addOnFailureListener(e -> handler.onError(e));
                }).addOnFailureListener(e -> handler.onError(e));

    }

    public static void storeUser(User user, DBUserHandler handler) {
        database.collection(USER_PATH).document(user.getUid()).set(user).addOnSuccessListener(unused ->
                handler.onSuccess(user)
        ).addOnFailureListener(
                handler::onError
        );
    }

    public static void storePosts(Post post, DBPostHandler handler) {
        CollectionReference reference = database.collection(USER_PATH).document(post.getUid()).collection(POST_PATH);
        String id = reference.document().getId();
        post.setId(id);

        reference.document(post.getId()).set(post).addOnSuccessListener(unused ->
                handler.onSuccess(post)
        ).addOnFailureListener(handler::onError);
    }

    public static void storeFeeds(Post post, DBPostHandler handler) {
        CollectionReference reference = database.collection(USER_PATH).document(post.getUid()).collection(FEED_PATH);
        reference.document(post.getId()).set(post).addOnSuccessListener(unused ->
                handler.onSuccess(post)
        ).addOnFailureListener(handler::onError);
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

    public static void updateUserImg(String userImg) {
        String uid = AuthManager.currentUser().getUid();
        database.collection(USER_PATH).document(uid).update("userImg", userImg);
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

    public static void loadFeeds(String uid, DBPostsHandler handler) {
        ArrayList<Post> posts = new ArrayList<>();
        CollectionReference reference = database.collection(USER_PATH).document(uid).collection(FEED_PATH);
        reference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String id = document.getString("id");
                    String caption = document.getString("caption");
                    String postImg = document.getString("postImg");
                    String fullname = document.getString("fullname");
                    String userImg = document.getString("userImg");
                    String currentDate = document.getString("currentDate");
                    Boolean isLiked = document.getBoolean("isLiked");
                    if (isLiked == null) isLiked = false;
                    String userId = document.getString("uid");

                    Post post = new Post(id, caption, postImg);
                    post.setUid(userId);
                    post.setFullname(fullname);
                    post.setUserImg(userImg);
                    post.setCurrentDate(currentDate);
                    post.setLiked(isLiked);
                    posts.add(post);
                }
                handler.onSuccess(posts);
            } else {
                handler.onError(task.getException());
            }
        });
    }

    public static void loadPosts(String uid, DBPostsHandler handler) {
        ArrayList<Post> posts = new ArrayList<>();
        CollectionReference reference = database.collection(USER_PATH).document(uid).collection(POST_PATH);
        reference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String id = document.getString("id");
                    String caption = document.getString("caption");
                    String postImg = document.getString("postImg");
                    String fullname = document.getString("fullname");
                    String userImg = document.getString("userImg");
                    String currentDate = document.getString("currentDate");
                    Boolean isLiked = document.getBoolean("isLiked");
                    if (isLiked == null) isLiked = false;
                    String userId = document.getString("uid");

                    Post post = new Post(id, caption, postImg);
                    post.setUid(userId);
                    post.setFullname(fullname);
                    post.setUserImg(userImg);
                    post.setCurrentDate(currentDate);
                    post.setLiked(isLiked);
                    posts.add(post);
                }
                handler.onSuccess(posts);
            } else {
                handler.onError(task.getException());
            }
        });
    }

    public static void storePostsToMyFeed(String uid, User to) {
        loadPosts(to.getUid(), new DBPostsHandler() {
            @Override
            public void onSuccess(ArrayList<Post> posts) {
                for (Post post : posts) {
                    storeFeed(uid, post);
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    public static void storeFeed(String uid, Post post) {
        CollectionReference reference = database.collection(USER_PATH).document(uid).collection(FEED_PATH);
        reference.document(post.getId()).set(post);
    }

    public static void removePostsFromMyFeed(String uid, User to) {
        loadPosts(to.getUid(), new DBPostsHandler() {
            @Override
            public void onSuccess(ArrayList<Post> posts) {
                for (Post post : posts
                ) {
                    removeFeed(uid, post);
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    public static void removeFeed(String uid, Post post) {
        CollectionReference reference = database.collection(USER_PATH).document(uid).collection(FEED_PATH);
        reference.document(post.getId()).delete();
    }

    public static void likeFeedPost(String uid, Post data) {
        database.collection(USER_PATH).document(uid).collection(FEED_PATH).document(data.getId())
                .update("isLiked", data.isLiked());

        if (uid.equals(data.getUid()))
            database.collection(USER_PATH).document(uid).collection(POST_PATH).document(data.getId())
                    .update("isLiked", data.isLiked());
    }

    public static void loadLikedFeeds(String uid, DBPostsHandler handler) {
        ArrayList<Post> posts = new ArrayList<>();
        Query reference;
        reference = database.collection(USER_PATH).document(uid).collection(FEED_PATH)
                .whereEqualTo("isLiked", true);
        reference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String id = document.getString("id");
                    String caption = document.getString("caption");
                    String postImg = document.getString("postImg");
                    String fullname = document.getString("fullname");
                    String userImg = document.getString("userImg");
                    String currentDate = document.getString("currentDate");
                    Boolean isLiked = document.getBoolean("isLiked");
                    if (isLiked == null) isLiked = false;
                    String userId = document.getString("uid");

                    Post post = new Post(id, caption, postImg);
                    post.setUid(userId);
                    post.setFullname(fullname);
                    post.setUserImg(userImg);
                    post.setCurrentDate(currentDate);
                    post.setLiked(isLiked);
                    posts.add(post);
                }
                handler.onSuccess(posts);
            } else {
                handler.onError(task.getException());
            }
        });

    }

    public static void deletePost(Post post, DBPostHandler handler) {
        CollectionReference reference1 = database.collection(USER_PATH).document(post.getUid()).collection(POST_PATH);
        reference1.document(post.getId()).delete().addOnSuccessListener(unused -> {
            CollectionReference reference2 = database.collection(USER_PATH).document(post.getUid()).collection(FEED_PATH);
            reference2.document(post.getId()).delete().addOnSuccessListener(unused1 ->
                    handler.onSuccess(post)
            ).addOnFailureListener(handler::onError);
        }).addOnFailureListener(handler::onError);
    }
}