package com.like.virtualapk.like;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.like.virtualapk.PluginManager;
import com.like.virtualapk.utils.FileIOUtils;
import com.like.virtualapk.utils.Reflector;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Button btnLoad;
    private static final String PLUGIN_RELATIVE_A_PATH = "plugin/pluginA.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            loadPluginA();
        } catch (Exception e) {
            e.printStackTrace();
        }
        btnLoad = findViewById(R.id.btn_load);
        btnLoad.setOnClickListener(v -> {
            try {
                //加载插件中的Test
                Reflector reflector = Reflector.on("com.like.virtualapk.plugin.Test");
                Object target = reflector.constructor().newInstance();
                reflector.bind(target).method("method").call();
            } catch (Reflector.ReflectedException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadPluginA() throws Exception {
        File file = new File(this.getFilesDir().getAbsolutePath() + File.separator + PLUGIN_RELATIVE_A_PATH);
        if (!file.exists()) {
            FileIOUtils.writeFileFromIS(file, this.getAssets().open(PLUGIN_RELATIVE_A_PATH));
        }
        PluginManager.getInstance(this).loadPlugin(file);
    }
}
