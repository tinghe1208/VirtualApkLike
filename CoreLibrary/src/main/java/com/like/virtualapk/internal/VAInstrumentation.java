package com.like.virtualapk.internal;

import android.app.Activity;
import android.app.Fragment;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.like.virtualapk.PluginManager;
import com.like.virtualapk.internal.utils.PluginUtil;

public class VAInstrumentation extends Instrumentation {

    public static final String TAG = Constants.TAG_PREFIX + "VAInstrumentation";

    private Instrumentation mBase;
    private PluginManager mPluginManager;

    public VAInstrumentation(PluginManager pluginManager, Instrumentation base) {
        this.mPluginManager = pluginManager;
        this.mBase = base;
    }

    @Override
    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity target, Intent intent, int requestCode) {
        injectIntent(intent);
        return super.execStartActivity(who, contextThread, token, target, intent, requestCode);
    }

    @Override
    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, String target, Intent intent, int requestCode, Bundle options) {
        injectIntent(intent);
        return super.execStartActivity(who, contextThread, token, target, intent, requestCode, options);
    }

    @Override
    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity target, Intent intent, int requestCode, Bundle options) {
        injectIntent(intent);
        return super.execStartActivity(who, contextThread, token, target, intent, requestCode, options);
    }

    @Override
    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Fragment target, Intent intent, int requestCode, Bundle options) {
        injectIntent(intent);
        return super.execStartActivity(who, contextThread, token, target, intent, requestCode, options);
    }

    private void injectIntent(Intent intent) {
        // null component is an implicitly intent
        if (intent.getComponent() != null) {
            // resolve intent with Stub Activity if needed
            this.mPluginManager.getComponentsHandler().markIntentIfNeeded(intent);
        }
    }

    @Override
    public Activity newActivity(ClassLoader cl, String className, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        try {
            cl.loadClass(className);
        } catch (ClassNotFoundException e) {
            ComponentName component = PluginUtil.getComponent(intent);
            if (component == null) {
                return mBase.newActivity(cl, className, intent);
            }
            String targetClassName = component.getClassName();
            Log.i(TAG, String.format("newActivity[%s : %s/%s]", className, component.getPackageName(), targetClassName));
            Activity activity = mBase.newActivity(cl, targetClassName, intent);
            activity.setIntent(intent);
            return activity;
        }

        return mBase.newActivity(cl, className, intent);
    }
}
