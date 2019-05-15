package com.like.virtualapk.internal;

import android.content.Context;

import com.like.virtualapk.internal.utils.DexUtil;

import java.io.File;

import dalvik.system.DexClassLoader;

public class LoadedPlugin {

    private ClassLoader mClassLoader;

    public LoadedPlugin(Context context, File apk) throws Exception {
        this.mClassLoader = createClassLoader(context, apk, context.getClassLoader());
    }

    protected File getDir(Context context, String name) {
        return context.getDir(name, Context.MODE_PRIVATE);
    }

    protected ClassLoader createClassLoader(Context context, File apk, ClassLoader parent) throws Exception {

        File dexOutputDir = getDir(context, Constants.OPTIMIZE_DIR);
        String dexOutputPath = dexOutputDir.getAbsolutePath();

        File nativeLibDir = getDir(context, Constants.NATIVE_DIR);

        DexClassLoader loader = new DexClassLoader(apk.getAbsolutePath(), dexOutputPath, nativeLibDir.getAbsolutePath(), parent);

        //将loader的dexElements添加到parent的dexElements中去。
        DexUtil.insertDex(loader, parent);

        return loader;
    }

}
