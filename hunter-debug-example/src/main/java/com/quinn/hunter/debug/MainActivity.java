package com.quinn.hunter.debug;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import com.hunter.library.debug.HunterDebug;
import com.hunter.library.debug.HunterLoggerHandler;
import com.quinn.hunter.debug.test.ClassTest;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HunterLoggerHandler.installLog(new HunterLoggerHandler() {
            @Override
            protected void logEnter(int priority, String tag, String methodName, String params) {
                Log.println(priority, "hunter-" + tag, String.format("<IN> %s::%s(%s)", tag, methodName, params));
            }

            @Override
            protected void logExit(int priority, String tag, String methodName, long costedMillis, String result) {
                Log.println(priority, "hunter-" + tag, String.format("<OUT> %s::%s(%sms) = %s", tag, methodName, costedMillis, result));
            }
        });

        setContentView(R.layout.activity_main);
        MainPresenter mainPresenter = new MainPresenter();
        mainPresenter.load("1212");
        mainPresenter.loadMore("12123232");
        boolean bool = true;
        byte byte_v = 1;
        char char_v = 2;
        short short_v = 3;
        int int_v = 4;
        long long_v = 5;
        float float_v = 6;
        double double_v = 7;
        String string_v = "string";
        int[] int_arr = new int[]{1, 2, 3};
        method_test_parameter(bool, byte_v, char_v, short_v, int_v, long_v, float_v, double_v, string_v, int_arr, savedInstanceState);
        method_empty_parameter_empty_return();
        method_return_array();
        method_return_boolean();
        method_return_byte();
        method_return_char();
        method_return_double();
        method_return_float();
        method_return_int();
        method_return_long();
        method_return_object();
        method_return_object_array();
        method_return_short();
        method_static("parameter value");
        appendIntAndString(5, "billions");
//        try {
//            method_throw_exception();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            testVoidMethodWithException();
//        } catch (Exception error) {
//        }
        ClassTest classTest = new ClassTest();
        classTest.test(5, "hello world", (byte) 2);
        classTest.test2();

        try {
            classTest.test3();
        } catch (Throwable t) {
        }
    }

    ;

    private String appendIntAndString(int a, String b) {
        SystemClock.sleep(100);
        return a + " " + b;
    }

    private List<String> paramNames = new ArrayList<>();


    @HunterDebug(debugResult = true, logLevel = Log.ERROR)
    private int method_test_parameter(boolean bool_v, byte byte_v, char char_v, short short_v, int int_v, long long_v, float float_v, double double_v, String string_v, int[] arr, Bundle savedInstanceState) {
        int insideLocal = 5;
        int insideLocal2 = 6;
        Log.i(TAG, "insideLocal " + insideLocal);
        return insideLocal + insideLocal2;
    }

    @HunterDebug(logLevel = Log.VERBOSE)
    private void method_empty_parameter_empty_return() {

    }

    private boolean method_return_boolean() {
        return true;
    }

    private char method_return_char() {
        return 'c';
    }

    private byte method_return_byte() {
        return 0x01;
    }

    private short method_return_short() {
        return 2;
    }

    private int method_return_int() {
        return 2;
    }

    private long method_return_long() {
        return 2L;
    }

    private double method_return_double() {
        return 2;
    }

    private float method_return_float() {
        return 2.0f;
    }

    private MainPresenter method_return_object() {
        return new MainPresenter();
    }

    private MainPresenter[] method_return_object_array() {
        return new MainPresenter[]{new MainPresenter(), new MainPresenter(), new MainPresenter()};
    }

    private int[] method_return_array() {
        return new int[]{1, 2, 3};
    }

    private static Object method_static(String str) {
        return "object string" + str;
    }

    private int method_throw_exception() throws Exception {
        int a = 10;
        int b = 0;
        if (b == 0) {
            throw new IllegalArgumentException("illagel argu");
        }
        int result = a / 0;
        return result;
    }

    private void testVoidMethodWithException() {
        throw new RuntimeException("not impl");
    }

}
