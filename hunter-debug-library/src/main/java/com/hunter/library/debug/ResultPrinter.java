package com.hunter.library.debug;

import android.util.Log;

import java.util.Arrays;

@SuppressWarnings ("unused")
public class ResultPrinter {

    public static void print(int level, String className, String methodName, long costedMillis, byte returnVal) {
//        Log.println(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal + ""));
        HunterLoggerHandler.DEFAULT_IMPL.log(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal + ""));
    }

    public static void print(int level, String className, String methodName, long costedMillis, char returnVal) {
//        Log.println(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal + ""));
        HunterLoggerHandler.DEFAULT_IMPL.log(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal + ""));
    }

    public static void print(int level, String className, String methodName, long costedMillis, short returnVal) {
//        Log.println(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal + ""));
        HunterLoggerHandler.DEFAULT_IMPL.log(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal + ""));
    }

    public static void print(int level, String className, String methodName, long costedMillis, int returnVal) {
//        Log.println(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal + ""));
        HunterLoggerHandler.DEFAULT_IMPL.log(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal + ""));
    }

    public static void print(int level, String className, String methodName, long costedMillis, boolean returnVal) {
//        Log.println(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal + ""));
        HunterLoggerHandler.DEFAULT_IMPL.log(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal + ""));
    }

    public static void print(int level, String className, String methodName, long costedMillis, long returnVal) {
//        Log.println(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal + ""));
        HunterLoggerHandler.DEFAULT_IMPL.log(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal + ""));
    }

    public static void print(int level, String className, String methodName, long costedMillis, float returnVal) {
//        Log.println(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal + ""));
        HunterLoggerHandler.DEFAULT_IMPL.log(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal + ""));
    }

    public static void print(int level, String className, String methodName, long costedMillis, double returnVal) {
//        Log.println(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal + ""));
        HunterLoggerHandler.DEFAULT_IMPL.log(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal + ""));
    }

    public static void print(int level, String className, String methodName, long costedMillis, Object returnVal) {
        if (returnVal != null && returnVal.getClass().isArray()) {
            Log.println(
                level, className,
                String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", arrayToString(returnVal))
            );
            HunterLoggerHandler.DEFAULT_IMPL.log(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", arrayToString(returnVal)));
        } else {
//            Log.println(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal));
            HunterLoggerHandler.DEFAULT_IMPL.log(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal + ""));
        }
    }

    public static void printWithCustomLogger(int level, String className, String methodName, long costedMillis, byte returnVal) {
        HunterLoggerHandler.CUSTOM_IMPL
            .log(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal + ""));
    }

    public static void printWithCustomLogger(int level, String className, String methodName, long costedMillis, char returnVal) {
        HunterLoggerHandler.CUSTOM_IMPL
            .log(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal + ""));
    }

    public static void printWithCustomLogger(int level, String className, String methodName, long costedMillis, short returnVal) {
        HunterLoggerHandler.CUSTOM_IMPL
            .log(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal + ""));
    }

    public static void printWithCustomLogger(int level, String className, String methodName, long costedMillis, int returnVal) {
        HunterLoggerHandler.CUSTOM_IMPL
            .log(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal + ""));
    }

    public static void printWithCustomLogger(int level, String className, String methodName, long costedMillis, boolean returnVal) {
        HunterLoggerHandler.CUSTOM_IMPL
            .log(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal + ""));
    }

    public static void printWithCustomLogger(int level, String className, String methodName, long costedMillis, long returnVal) {
        HunterLoggerHandler.CUSTOM_IMPL
            .log(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal + ""));
    }

    public static void printWithCustomLogger(int level, String className, String methodName, long costedMillis, float returnVal) {
        HunterLoggerHandler.CUSTOM_IMPL
            .log(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal + ""));
    }

    public static void printWithCustomLogger(int level, String className, String methodName, long costedMillis, double returnVal) {
        HunterLoggerHandler.CUSTOM_IMPL
            .log(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal + ""));
    }

    public static void printWithCustomLogger(int level, String className, String methodName, long costedMillis, Object returnVal) {
        if (returnVal != null && returnVal.getClass().isArray()) {
            HunterLoggerHandler.CUSTOM_IMPL.log(level, className,
                String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", arrayToString(returnVal))
            );
        } else {
            HunterLoggerHandler.CUSTOM_IMPL
                .log(level, className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMillis + "", returnVal));
        }
    }

    private static String arrayToString(Object val) {
        if (!(val instanceof Object[])) {
            if (val instanceof int[]) {
                return Arrays.toString((int[]) val);
            } else if (val instanceof char[]) {
                return Arrays.toString((char[]) val);
            } else if (val instanceof boolean[]) {
                return Arrays.toString((boolean[]) val);
            } else if (val instanceof byte[]) {
                return Arrays.toString((byte[]) val);
            } else if (val instanceof long[]) {
                return Arrays.toString((long[]) val);
            } else if (val instanceof double[]) {
                return Arrays.toString((double[]) val);
            } else if (val instanceof float[]) {
                return Arrays.toString((float[]) val);
            } else if (val instanceof short[]) {
                return Arrays.toString((short[]) val);
            } else {
                return "Unknown type array";
            }
        } else {
            return Arrays.deepToString((Object[]) val);
        }
    }

}
