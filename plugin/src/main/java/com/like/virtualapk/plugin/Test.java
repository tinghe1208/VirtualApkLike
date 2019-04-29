package com.like.virtualapk.plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test {

    public void method() {
        try {
            Class<?> appTestClass = Class.forName("com.like.virtualapk.AppTest");
            Object target = appTestClass.getConstructor().newInstance();
            Method method = appTestClass.getMethod("method");
            method.invoke(target);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
