package com.uz.instaclonejava.manager.handler;

import com.uz.instaclonejava.model.User;

/**
 * Created by Eldor Turgunov on 16.08.2022.
 * Insta Clone Java
 * eldorturgunov777@gmail.com
 */

public interface DBUserHandler {

    public void onSuccess(User user);

    public void onError(Exception e);
}
