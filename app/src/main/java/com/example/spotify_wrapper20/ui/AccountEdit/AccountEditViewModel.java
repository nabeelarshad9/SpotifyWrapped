package com.example.spotify_wrapper20.ui.AccountEdit;

import androidx.lifecycle.ViewModel;

public class AccountEditViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private static String mUserName;
    private static String mPassword;
    public String getUserName() {
        return mUserName;
    }
    public String getPassword() {
        return mPassword;
    }
    public void setUserName(String userName) {
        mUserName = userName;
    }
    public void setPassword(String password) {
        mPassword = password;
    }
}