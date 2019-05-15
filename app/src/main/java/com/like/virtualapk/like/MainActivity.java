package com.like.virtualapk.like;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.like.virtualapk.PluginManager;
import com.like.virtualapk.utils.FileIOUtils;
import com.like.virtualapk.utils.Reflector;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Button btnLoad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLoad = findViewById(R.id.btn_load);
        btnLoad.setOnClickListener(v -> {
            Intent intent = new Intent();
            ComponentName componentName = new ComponentName("om.like.virtualapk.plugin","com.like.virtualapk.plugin.PluginActivity");
            intent.setComponent(componentName);
            startActivity(intent);
        });
    }
}
