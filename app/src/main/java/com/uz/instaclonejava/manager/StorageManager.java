package com.uz.instaclonejava.manager;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.uz.instaclonejava.manager.handler.StorageHandler;

/**
 * Created by Eldor Turgunov on 16.08.2022.
 * Insta Clone Java
 * eldorturgunov777@gmail.com
 */
public class StorageManager {
    private static final String USER_PHOTO_PATH = "users";
    private final String POST_PHOTO_PATH = "posts";

    private final static FirebaseStorage storage = FirebaseStorage.getInstance();
    private final static StorageReference storageRef = storage.getReference();

    public static void uploadPostPhoto(Uri uri, StorageHandler handler) {

    }

    public static void uploadUserPhoto(Uri uri, StorageHandler handler) {
        String filename = AuthManager.currentUser().getUid() + ".png";
        UploadTask uploadTask = storageRef.child(USER_PHOTO_PATH).child(filename).putFile(uri);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
            result.addOnSuccessListener(uri1 -> {
                String imgUrl = uri1.toString();
                handler.onSuccess(imgUrl);
            }).addOnFailureListener(handler::onError);
        }).addOnFailureListener(handler::onError);
    }
}
