package com.quinn.hunter.plugin.debug.bytecode;

import com.quinn.hunter.plugin.debug.Constants;

public class MethodDataHolder {
    private final String name;
    private final String methodSignature;
    private boolean debugEnabled = true;
    private boolean debugOutput = Constants.DEBUG_RESULT;
    private boolean debugInput = true;
    private int logLevel = Constants.LOG_LEVEL;
    private int debugArguments = Constants.DEBUG_ARGUMENTS;

    public MethodDataHolder(String n, String key) {
        this.name = n;
        this.methodSignature = key;
    }

    public String getMethodUniqueKey() {
        return name + methodSignature;
    }

    public String getMethodSignature() {
        return methodSignature;
    }

    public String getName() {
        return name;
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public void setDebugEnabled(final boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    public int getDebugArguments() {
        return debugArguments;
    }

    public void setDebugArguments(final int value) {
        this.debugArguments = value;
    }

    public boolean isDebugOutput() {
        return debugOutput;
    }

    public void setDebugOutput(final boolean debugOutput) {
        this.debugOutput = debugOutput;
    }

    public boolean isDebugInput() {
        return debugInput;
    }

    public void setDebugInput(final boolean debugInput) {
        this.debugInput = debugInput;
    }

    public void setLogLevel(int level) {
        this.logLevel = level;
    }

    public int getLogLevel() {
        return logLevel;
    }

    @Override
    public String toString() {
        return "MethodDataHolder{" +
                "name='" + name + '\'' +
                ", debugOutput=" + debugOutput +
                ", logLevel=" + logLevel +
                '}';
    }

}
