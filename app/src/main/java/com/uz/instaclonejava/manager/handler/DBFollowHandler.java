package com.uz.instaclonejava.manager.handler;

/**
 * Created by Eldor Turgunov on 16.08.2022.
 * Insta Clone Java
 * eldorturgunov777@gmail.com
 */

public interface DBFollowHandler {

    public void onSuccess(Boolean isDone);

    public void onError(Exception e);
}
