package com.like.virtualapk;

import android.content.Context;

import com.like.virtualapk.internal.LoadedPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class PluginManager {

    private static volatile PluginManager sInstance = null;
    protected Context mContext;

    private PluginManager(Context context) {
        this.mContext = context;
    }

    public static PluginManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (PluginManager.class) {
                if (sInstance == null) {
                    sInstance = new PluginManager(context);
                }
            }
        }
        return sInstance;
    }

    /**
     * load a plugin into memory
     *
     * @param apk the file of plugin, should end with .apk
     * @throws Exception
     */
    public void loadPlugin(File apk) throws Exception {
        if (null == apk) {
            throw new IllegalArgumentException("error : apk is null.");
        }

        if (!apk.exists()) {
            // throw the FileNotFoundException by opening a stream.
            InputStream in = new FileInputStream(apk);
            in.close();
        }
        new LoadedPlugin(this.mContext, apk);

    }

}
