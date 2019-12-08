package com.quinn.hunter.plugin.debug.bytecode.prego;

import com.android.build.gradle.internal.LoggerWrapper;
import com.quinn.hunter.plugin.debug.bytecode.MethodDataHolder;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;

public class DebugPreGoAnnotationAdapter extends AnnotationVisitor {
    private static final LoggerWrapper logger = LoggerWrapper.getLogger(DebugPreGoAnnotationAdapter.class);
    private final MethodDataHolder method;

    private boolean debugResult = true;

    public DebugPreGoAnnotationAdapter(
        final MethodDataHolder method, final AnnotationVisitor av) {
        super(Opcodes.ASM5, av);
        this.method = method;
    }

    @Override
    public void visit(final String name, final Object value) {
        if ("debugResult".equals(name)) {
            debugResult = (boolean) value;
        }

        super.visit(name, value);
    }

    @Override
    public void visitEnum(final String name, final String desc, final String value) {
        super.visitEnum(name, desc, value);
    }

    @Override
    public void visitEnd() {
        this.method.setDebugOutput(debugResult);
        super.visitEnd();
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
