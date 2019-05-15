package com.like.virtualapk;

import android.app.ActivityThread;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.util.Log;

import com.like.virtualapk.internal.ComponentsHandler;
import com.like.virtualapk.internal.Constants;
import com.like.virtualapk.internal.LoadedPlugin;
import com.like.virtualapk.internal.VAInstrumentation;
import com.like.virtualapk.utils.Reflector;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class PluginManager {
    private static final String TAG = Constants.TAG_PREFIX + "PluginManager";

    private static volatile PluginManager sInstance = null;
    // Context of host app
    private Context mContext;
    private Application mApplication;
    private ComponentsHandler mComponentsHandler;

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

    private PluginManager(Context context) {
        if (context instanceof Application) {
            this.mApplication = (Application) context;
            this.mContext = mApplication.getBaseContext();
        } else {
            final Context app = context.getApplicationContext();
            if (app == null) {
                this.mContext = context;
                this.mApplication = ActivityThread.currentApplication();
            } else {
                this.mApplication = (Application) app;
                this.mContext = mApplication.getBaseContext();
            }
        }

        this.mComponentsHandler = new ComponentsHandler(this);
        hookInstrumentation();
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

    protected void hookInstrumentation() {
        try {
            ActivityThread activityThread = ActivityThread.currentActivityThread();
            Instrumentation baseInstrumentation = activityThread.getInstrumentation();
            final VAInstrumentation instrumentation = createInstrumentation(baseInstrumentation);
            Reflector.with(activityThread).field("mInstrumentation").set(instrumentation);
        } catch (Exception e) {
            Log.w(TAG, e);
        }
    }

    private VAInstrumentation createInstrumentation(Instrumentation origin) {
        return new VAInstrumentation(this, origin);
    }

    public Context getHostContext() {
        return mContext;
    }

    public ComponentsHandler getComponentsHandler() {
        return mComponentsHandler;
    }

}
