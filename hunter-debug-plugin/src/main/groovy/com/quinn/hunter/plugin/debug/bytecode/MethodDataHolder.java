package com.quinn.hunter.plugin.debug.bytecode;

public class MethodDataHolder {
    private final String name;
    private final String methodSignature;
    private boolean debugEnabled = true;
    private boolean debugOutput = true;
    private boolean debugInput = true;

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

    @Override
    public String toString() {
        return "MethodPresenter{" +
            "name='" + name + '\'' +
            ", debugOutput=" + debugOutput +
            '}';
    }
}
