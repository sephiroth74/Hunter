package com.hunter.library.debug;

import java.util.Arrays;

@SuppressWarnings("unused")
public class ResultPrinter {
    public static void print(int level, int printArguments, String className, String methodName, long costedMillis, byte returnVal) {
        HunterLoggerHandler.DEFAULT_IMPL.logExit(level, className, methodName, costedMillis, returnVal + "");
    }

    public static void print(int level, int printArguments, String className, String methodName, long costedMillis, char returnVal) {
        HunterLoggerHandler.DEFAULT_IMPL.logExit(level, className, methodName, costedMillis, returnVal + "");
    }

    public static void print(int level, int printArguments, String className, String methodName, long costedMillis, short returnVal) {
        HunterLoggerHandler.DEFAULT_IMPL.logExit(level, className, methodName, costedMillis, returnVal + "");
    }

    public static void print(int level, int printArguments, String className, String methodName, long costedMillis, int returnVal) {
        HunterLoggerHandler.DEFAULT_IMPL.logExit(level, className, methodName, costedMillis, returnVal + "");
    }

    public static void print(int level, int printArguments, String className, String methodName, long costedMillis, boolean returnVal) {
        HunterLoggerHandler.DEFAULT_IMPL.logExit(level, className, methodName, costedMillis, returnVal + "");
    }

    public static void print(int level, int printArguments, String className, String methodName, long costedMillis, long returnVal) {
        HunterLoggerHandler.DEFAULT_IMPL.logExit(level, className, methodName, costedMillis, returnVal + "");
    }

    public static void print(int level, int printArguments, String className, String methodName, long costedMillis, float returnVal) {
        HunterLoggerHandler.DEFAULT_IMPL.logExit(level, className, methodName, costedMillis, returnVal + "");
    }

    public static void print(int level, int printArguments, String className, String methodName, long costedMillis, double returnVal) {
        HunterLoggerHandler.DEFAULT_IMPL.logExit(level, className, methodName, costedMillis, returnVal + "");
    }

    public static void print(int level, int printArguments, String className, String methodName, long costedMillis, Object returnVal) {
        if (printArguments == Constants.ARGUMENTS_NONE) {
            HunterLoggerHandler.DEFAULT_IMPL.logExit(level, className, methodName, costedMillis, "");
        } else {
            if (returnVal != null && returnVal.getClass().isArray()) {
                if (printArguments == Constants.ARGUMENTS_FULL) {
                    HunterLoggerHandler.DEFAULT_IMPL.logExit(level, className, methodName, costedMillis, arrayToString(returnVal));
                } else if (printArguments == Constants.ARGUMENTS_SHORT) {
                    HunterLoggerHandler.DEFAULT_IMPL.logExit(level, className, methodName, costedMillis, ParameterPrinter.arrayToHashCode(returnVal));
                }
            } else if (returnVal instanceof String || returnVal instanceof Enum) {
                HunterLoggerHandler.DEFAULT_IMPL.logExit(level, className, methodName, costedMillis, returnVal + "");
            } else {
                if (printArguments == Constants.ARGUMENTS_FULL) {
                    HunterLoggerHandler.DEFAULT_IMPL.logExit(level, className, methodName, costedMillis, returnVal + "");
                } else if (printArguments == Constants.ARGUMENTS_SHORT) {
                    HunterLoggerHandler.DEFAULT_IMPL.logExit(level, className, methodName, costedMillis, ParameterPrinter.objectToHashCode(returnVal) + "");
                }
            }
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
