package com.like.virtualapk.internal;

import android.content.Context;
import android.content.Intent;

import com.like.virtualapk.PluginManager;

public class ComponentsHandler {

    private Context mContext;
    private PluginManager mPluginManager;

    public ComponentsHandler(PluginManager pluginManager) {
        this.mContext = pluginManager.getHostContext();
        this.mPluginManager = pluginManager;
    }


    public void markIntentIfNeeded(Intent intent) {
        if (intent.getComponent() == null) {
            return;
        }

        String targetPackageName = intent.getComponent().getPackageName();
        String targetClassName = intent.getComponent().getClassName();
        // search map and return specific launchmode stub activity
        if (!targetPackageName.equals(mContext.getPackageName())) {
            intent.putExtra(Constants.KEY_IS_PLUGIN, true);
            intent.putExtra(Constants.KEY_TARGET_PACKAGE, targetPackageName);
            intent.putExtra(Constants.KEY_TARGET_ACTIVITY, targetClassName);
            dispatchStubActivity(intent);
        }
    }

    private void dispatchStubActivity(Intent intent) {
        String stubActivity = "com.like.virtualapk.ProxyActivity";
        intent.setClassName(mContext, stubActivity);
    }
}
