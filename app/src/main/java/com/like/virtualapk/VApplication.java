package com.like.virtualapk;

import android.app.Application;

public class VApplication extends Application {

    public static VApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }
}
