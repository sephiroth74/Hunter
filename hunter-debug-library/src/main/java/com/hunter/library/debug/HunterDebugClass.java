package com.hunter.library.debug;

import android.util.Log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE}) @Retention(RetentionPolicy.CLASS)
@SuppressWarnings ("DefaultAnnotationParam")
public @interface HunterDebugClass {
    boolean debugResult() default true;
    int logLevel() default Log.INFO;
    boolean debugArguments() default true;
}
