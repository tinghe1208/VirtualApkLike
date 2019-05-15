/*
 * Copyright (C) 2017 Beijing Didi Infinity Technology and Development Co.,Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.like.virtualapk.internal.utils;

import com.like.virtualapk.utils.Reflector;

import java.lang.reflect.Array;

import dalvik.system.DexClassLoader;

public class DexUtil {

    public static void insertDex(DexClassLoader dexClassLoader, ClassLoader baseClassLoader) throws Exception {
        Object baseDexElements = getDexElements(getPathList(baseClassLoader));
        Object newDexElements = getDexElements(getPathList(dexClassLoader));
        Object allDexElements = combineArray(baseDexElements, newDexElements);
        Object pathList = getPathList(baseClassLoader);
        Reflector.with(pathList).field("dexElements").set(allDexElements);
    }

    private static Object getDexElements(Object pathList) throws Exception {
        return Reflector.with(pathList).field("dexElements").get();
    }

    private static Object getPathList(ClassLoader baseDexClassLoader) throws Exception {
        return Reflector.with(baseDexClassLoader).field("pathList").get();
    }

    private static Object combineArray(Object firstArray, Object secondArray) {
        Class<?> localClass = firstArray.getClass().getComponentType();
        int firstArrayLength = Array.getLength(firstArray);
        int secondArrayLength = Array.getLength(secondArray);
        Object result = Array.newInstance(localClass, firstArrayLength + secondArrayLength);
        System.arraycopy(firstArray, 0, result, 0, firstArrayLength);
        System.arraycopy(secondArray, 0, result, firstArrayLength, secondArrayLength);
        return result;
    }
}