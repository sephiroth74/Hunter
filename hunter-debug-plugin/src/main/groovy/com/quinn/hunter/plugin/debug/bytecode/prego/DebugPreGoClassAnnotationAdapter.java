package com.quinn.hunter.plugin.debug.bytecode.prego;

import com.android.build.gradle.internal.LoggerWrapper;
import com.quinn.hunter.plugin.debug.Constants;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;

public class DebugPreGoClassAnnotationAdapter extends AnnotationVisitor {
    private static final LoggerWrapper logger = LoggerWrapper.getLogger(DebugPreGoClassAnnotationAdapter.class);
    private final DebugPreGoClassAdapter.ClassCollector callback;

    private boolean debugResult = Constants.DEBUG_RESULT;
    private boolean debugArguments = Constants.DEBUG_ARGUMENTS;
    private int logLevel = Constants.LOG_LEVEL;

    public DebugPreGoClassAnnotationAdapter(final AnnotationVisitor av, DebugPreGoClassAdapter.ClassCollector callback) {
        super(Opcodes.ASM7, av);
        this.callback = callback;
    }

    public boolean isDebugResult() {
        return debugResult;
    }

    public boolean isDebugArguments() {
        return debugArguments;
    }

    public int getLogLevel() {
        return logLevel;
    }

    @Override
    public void visit(final String name, final Object value) {
        if ("debugResult".equals(name)) {
            debugResult = (boolean) value;
        } else if ("logLevel".equals(name)) {
            logLevel = (int) value;
        } else if ("debugArguments".equals(name)) {
            debugArguments = (boolean) value;
        }

        super.visit(name, value);
    }

    @Override
    public void visitEnum(final String name, final String desc, final String value) {
        super.visitEnum(name, desc, value);
    }

    @Override
    public void visitEnd() {
        super.visitEnd();

        if (null != callback) {
            callback.onIncludeClass(debugResult, logLevel, debugArguments);
        }
    }

    @Override
    public AnnotationVisitor visitAnnotation(final String name, final String desc) {
        return super.visitAnnotation(name, desc);
    }

    @Override
    public AnnotationVisitor visitArray(final String name) {
        return super.visitArray(name);
    }
}
