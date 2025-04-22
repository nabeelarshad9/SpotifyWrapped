package com.example.spotify_wrapper20.ui.wrapped;

import androidx.lifecycle.ViewModel;

public class WrappedViewModel extends ViewModel {

    private static String mAccessToken;


    public String getAccessToken() {
        return mAccessToken;
    }
    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }
}