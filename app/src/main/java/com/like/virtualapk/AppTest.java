package com.like.virtualapk;

import android.content.Context;
import android.widget.Toast;

public class AppTest {

    public void method() {
        Toast.makeText(VApplication.INSTANCE, "I am from app", Toast.LENGTH_LONG).show();
    }
}
