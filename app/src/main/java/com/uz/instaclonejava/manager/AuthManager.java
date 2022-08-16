package com.uz.instaclonejava.manager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.uz.instaclonejava.manager.handler.AuthHandler;

public class AuthManager {
    static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public static Boolean isSignedIn() {
        return currentUser() != null;
    }

    public static FirebaseUser currentUser() {
        return firebaseAuth.getCurrentUser();
    }

    public static void signIn(String email, String password, AuthHandler handler) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String uid = currentUser().getUid();
                handler.onSuccess(uid);
            } else {
                handler.onError(task.getException());
            }
        });
    }

    public static void signUp(String email, String password, AuthHandler handler) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String uid = currentUser().getUid();
                handler.onSuccess(uid);
            } else {
                handler.onError(task.getException());
            }
        });
    }

    public static void signOut() {
        firebaseAuth.signOut();
    }
}
