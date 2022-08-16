package com.uz.instaclonejava.manager.handler;

import com.uz.instaclonejava.model.User;

import java.util.ArrayList;

/**
 * Created by Eldor Turgunov on 16.08.2022.
 * Insta Clone Java
 * eldorturgunov777@gmail.com
 */

public interface DBUsersHandler {

    public void onSuccess(ArrayList<User> users);

    public void onError(Exception e);
}
