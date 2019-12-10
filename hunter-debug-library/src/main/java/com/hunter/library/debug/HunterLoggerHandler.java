package com.hunter.library.debug;

import android.util.Log;

/**
 * Created by quinn on 2019/1/8
 */
@SuppressWarnings("unused")
public class HunterLoggerHandler {

    protected void logEnter(int priority, String tag, String methodName, String params) { }

    protected void logExit(int priority, String tag, String methodName, long costedMillis, String result) { }

    public static final HunterLoggerHandler NullLogger = new HunterLoggerHandler();

    public static final HunterLoggerHandler DefaultLogger = new HunterLoggerHandler() {

        @Override
        protected void logEnter(int priority, String tag, String methodName, String params) {
            Log.println(priority, tag, String.format("⇢ %s[%s]", methodName, params));
        }

        @Override
        protected void logExit(int priority, String tag, String methodName, long costedMillis, String result) {
            Log.println(priority, tag, String.format("⇠ %s[%sms] = %s", methodName, costedMillis, result));
        }
    };

    static HunterLoggerHandler DEFAULT_IMPL = DefaultLogger;

    public static void installLog(HunterLoggerHandler loggerHandler) {
        DEFAULT_IMPL = loggerHandler;
    }

}
