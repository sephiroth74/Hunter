package com.hunter.library.debug;

import android.util.Log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface HunterDebugClass {
    boolean debugResult() default true;

    int logLevel() default Log.INFO;

    /**
     * Must be one of {@link Constants#ARGUMENTS_FULL}, {@link Constants#ARGUMENTS_SHORT} or {@link Constants#ARGUMENTS_NONE}
     *
     * @return
     */
    int debugArguments() default Constants.ARGUMENTS_FULL;
}
