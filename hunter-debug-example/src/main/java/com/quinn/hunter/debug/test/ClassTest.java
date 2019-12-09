package com.quinn.hunter.debug.test;

import android.util.Log;

import com.hunter.library.debug.HunterDebug;
import com.hunter.library.debug.ParameterPrinter;

public class ClassTest {
    public void test(int level) {
        ParameterPrinter presenter = new ParameterPrinter("ciao", "test");
        presenter.print(Log.ERROR);
    }

    @HunterDebug(logLevel = Log.ASSERT)
    public void test2() {
    }
}
