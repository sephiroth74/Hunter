package com.quinn.hunter.plugin.debug.bytecode.prego;

import com.android.build.gradle.internal.LoggerWrapper;
import com.quinn.hunter.plugin.debug.Constants;
import com.quinn.hunter.plugin.debug.bytecode.MethodDataHolder;
import com.quinn.hunter.plugin.debug.bytecode.Parameter;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Quinn on 16/09/2018.
 */
public final class DebugPreGoClassAdapter extends ClassVisitor {
    private static final LoggerWrapper logger = LoggerWrapper.getLogger(DebugPreGoClassAdapter.class);

    private Map<String, List<Parameter>> methodParametersMap = new HashMap<>();
    private DebugPreGoMethodAdapter debugPreGoMethodAdapter;
    private boolean needParameter = false;
    private boolean classDebug = false;
    private int logLevel = Constants.LOG_LEVEL;
    private boolean debugOutput = Constants.DEBUG_RESULT;
    private int debugArguments = Constants.DEBUG_ARGUMENTS;
    private HashMap<String, MethodDataHolder> includes = new HashMap<>();

    public DebugPreGoClassAdapter(final ClassVisitor cv) {
        super(Opcodes.ASM7, cv);
    }

    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        super.visitInnerClass(name, outerName, innerName, access);
    }

    @Override
    public void visitOuterClass(String owner, String name, String descriptor) {
        super.visitOuterClass(owner, name, descriptor);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        AnnotationVisitor origin = super.visitAnnotation(desc, visible);
        if ("Lcom/hunter/library/debug/HunterDebugClass;".equals(desc)) {
            origin = new DebugPreGoClassAnnotationAdapter(origin, (output, level, arguments) -> {
                debugOutput = output;
                logLevel = level;
                debugArguments = arguments;
            });
            classDebug = true;
        }
        return origin;
    }

    @Override
    public MethodVisitor visitMethod(
            final int access, final String name,
            final String desc, final String signature, final String[] exceptions) {

        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        String methodUniqueKey = name + desc;

        MethodDataHolder methodDataHolder = new MethodDataHolder(name, desc);
        methodDataHolder.setDebugOutput(debugOutput);
        methodDataHolder.setLogLevel(logLevel);
        methodDataHolder.setDebugArguments(debugArguments);

        debugPreGoMethodAdapter =
                new DebugPreGoMethodAdapter(methodDataHolder, methodUniqueKey, methodParametersMap, mv, classDebug,
                        method -> {
                            includes.put(method.getMethodUniqueKey(), methodDataHolder);
                            needParameter = true;
                        }
                );
        return mv == null ? null : debugPreGoMethodAdapter;
    }

    public Map<String, List<Parameter>> getMethodParametersMap() {
        return this.methodParametersMap;
    }

    public HashMap<String, MethodDataHolder> getIncludes() {
        return includes;
    }

    public boolean isNeedParameter() {
        return needParameter;
    }


    interface MethodCollector {
        void onIncludeMethod(MethodDataHolder method);
    }

    interface ClassCollector {
        void onIncludeClass(boolean debugOutput, int logLevel, int debugArguments);
    }
}
