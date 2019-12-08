package com.quinn.hunter.plugin.debug.bytecode.prego;

import com.android.build.gradle.internal.LoggerWrapper;
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
    private HashMap<String, MethodDataHolder> includes = new HashMap<>();
    private HashMap<String, MethodDataHolder> impls = new HashMap<>();

    public DebugPreGoClassAdapter(final ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        AnnotationVisitor orgin = super.visitAnnotation(desc, visible);
        if ("Lcom/hunter/library/debug/HunterDebugClass;".equals(desc)) {
            classDebug = true;
        }
        return orgin;
    }

    @Override
    public MethodVisitor visitMethod(
        final int access, final String name,
        final String desc, final String signature, final String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        String methodUniqueKey = name + desc;

        MethodDataHolder methodDataHolder = new MethodDataHolder(name, desc);

        debugPreGoMethodAdapter = new DebugPreGoMethodAdapter(methodDataHolder, methodUniqueKey, methodParametersMap, mv, classDebug,
            (method, useImpl) -> {
                if (useImpl) {
                    impls.put(method.getMethodUniqueKey(), method);
                }
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

    public HashMap<String, MethodDataHolder> getImpls() {
        return impls;
    }

    public boolean isNeedParameter() {
        return needParameter;
    }

    interface MethodCollector {
        void onIncludeMethod(MethodDataHolder methodName, boolean useImpl);
    }
}