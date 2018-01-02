package com.example.hung.myapplication.interfaces;

/**
 * Created by Hung on 11/19/2017.
 */

public interface MainActivityInterface {
    public void GetInfoFromSignIn(boolean toSignUp, String email, String password);
    public void GetInfoFromSignUp(boolean toSignIn, String email, String password, boolean success);
}
