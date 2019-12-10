package com.hunter.library.debug;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE}) @Retention(RetentionPolicy.CLASS)
public @interface HunterDebugClass {
    boolean debugResult() default true;
    int logLevel() default Constants.LOG_LEVEL;
}
