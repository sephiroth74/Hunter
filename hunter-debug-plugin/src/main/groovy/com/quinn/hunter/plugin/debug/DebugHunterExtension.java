package com.quinn.hunter.plugin.debug;

import com.quinn.hunter.transform.RunVariant;

/**
 * Created by Quinn on 07/10/2018.
 */
public class DebugHunterExtension {

    public boolean debugResult = Constants.DEBUG_RESULT;

    public int logLevel = Constants.LOG_LEVEL;

    public RunVariant runVariant = RunVariant.ALWAYS;

    public boolean duplcatedClassSafeMode = false;

    public void debugResult(boolean value) { this.debugResult = value; }

    public void logLevel(int level) { this.logLevel = level; }

    @Override
    public String toString() {
        return "DebugHunterExtension{" +
            "runVariant=" + runVariant +
            ", duplcatedClassSafeMode=" + duplcatedClassSafeMode +
            ", debugResult=" + debugResult +
            ", logLevel=" + logLevel +
            '}';
    }
}
