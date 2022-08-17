package com.uz.instaclonejava.manager.handler;

import com.uz.instaclonejava.model.Post;

import java.util.ArrayList;

/**
 * Created by Eldor Turgunov on 16.08.2022.
 * Insta Clone Java
 * eldorturgunov777@gmail.com
 */

public interface DBPostsHandler {

    public void onSuccess(ArrayList<Post> post);

    public void onError(Exception e);
}
