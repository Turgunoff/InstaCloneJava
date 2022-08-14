package com.uz.instaclonejava.manager.handler;

public interface AuthHandler {

    public void onSuccess(String uid);

    public void onError(Exception exception);
}
