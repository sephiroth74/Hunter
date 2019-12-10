package com.quinn.hunter.debug.test;

import android.util.Log;

import com.hunter.library.debug.HunterDebug;
import com.hunter.library.debug.HunterDebugClass;
import com.hunter.library.debug.HunterDebugSkip;

@HunterDebugClass(logLevel = Log.WARN, debugResult = false)
public class ClassTest {

    public void test(int level) {
    }

    @HunterDebugSkip
    public void test2() {
    }

    @HunterDebug(logLevel = Log.VERBOSE, debugResult = true)
    public int test3() {
        int i = 0;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("che palle!");
    }

}
