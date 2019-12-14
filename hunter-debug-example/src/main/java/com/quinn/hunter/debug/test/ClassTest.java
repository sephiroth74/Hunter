package com.quinn.hunter.debug.test;

import com.hunter.library.debug.HunterDebug;
import com.hunter.library.debug.HunterDebugClass;
import com.hunter.library.debug.HunterDebugSkip;

import androidx.core.util.Pair;

@HunterDebugClass
public class ClassTest {

    public Pair<Integer, String> test(int level, String type, byte value) {
        return Pair.create(level, type);
    }

    @HunterDebugSkip
    public void test2() {
    }

    @HunterDebug(logLevel = 4, debugResult = true)
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
