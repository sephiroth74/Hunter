package com.quinn.hunter.debug.test;

import android.util.Log;

import com.hunter.library.debug.HunterDebug;
import com.hunter.library.debug.HunterDebugClass;

@HunterDebugClass
public class ClassTest {

    public void test(int level) {
    }

    @HunterDebug (logLevel = Log.INFO, debugResult = false)
    public void test2() {
    }

    @HunterDebug (logLevel = Log.WARN)
    public int test3() {
        int i = 0;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("che palle!");
        //        return i;

    }

}
