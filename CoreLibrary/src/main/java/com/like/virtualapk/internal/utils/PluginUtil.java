package com.like.virtualapk.internal.utils;

import android.content.ComponentName;
import android.content.Intent;

import com.like.virtualapk.internal.Constants;

public class PluginUtil {

    public static boolean isIntentFromPlugin(Intent intent) {
        if (intent == null) {
            return false;
        }
        return intent.getBooleanExtra(Constants.KEY_IS_PLUGIN, false);
    }

    public static ComponentName getComponent(Intent intent) {
        if (intent == null) {
            return null;
        }
        if (isIntentFromPlugin(intent)) {
            return new ComponentName(intent.getStringExtra(Constants.KEY_TARGET_PACKAGE),
                    intent.getStringExtra(Constants.KEY_TARGET_ACTIVITY));
        }

        return intent.getComponent();
    }
}
