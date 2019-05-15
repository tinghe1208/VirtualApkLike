package com.like.virtualapk;

import android.app.Application;

import com.like.virtualapk.utils.FileIOUtils;

import java.io.File;

public class VApplication extends Application {

    public static VApplication INSTANCE;
    private static final String PLUGIN_RELATIVE_A_PATH = "plugin/pluginA.apk";

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        try {
            loadPluginA();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadPluginA() throws Exception {
        File file = new File(this.getFilesDir().getAbsolutePath() + File.separator + PLUGIN_RELATIVE_A_PATH);
        if (!file.exists()) {
            FileIOUtils.writeFileFromIS(file, this.getAssets().open(PLUGIN_RELATIVE_A_PATH));
        }
        PluginManager.getInstance(this).loadPlugin(file);
    }
}
