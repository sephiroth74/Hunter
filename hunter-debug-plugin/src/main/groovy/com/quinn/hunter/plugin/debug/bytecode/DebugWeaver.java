package com.quinn.hunter.plugin.debug.bytecode;

import com.android.build.gradle.internal.LoggerWrapper;
import com.quinn.hunter.plugin.debug.Constants;
import com.quinn.hunter.plugin.debug.bytecode.prego.DebugPreGoClassAdapter;
import com.quinn.hunter.transform.asm.BaseWeaver;
import com.quinn.hunter.transform.asm.ExtendClassWriter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Quinn on 16/09/2018.
 */
public final class DebugWeaver extends BaseWeaver {
    @SuppressWarnings("unused")
    private static final LoggerWrapper logger = LoggerWrapper.getLogger(DebugWeaver.class);

    @Override
    public void setExtension(Object extension) {
    }

    @Override
    public byte[] weaveSingleClassToByteArray(InputStream inputStream) throws IOException {
        ClassReader classReader = new ClassReader(inputStream);
        ClassWriter classWriter = new ExtendClassWriter(classLoader, ClassWriter.COMPUTE_MAXS);
        DebugPreGoClassAdapter debugPreGoClassAdapter = new DebugPreGoClassAdapter(classWriter);
        classReader.accept(debugPreGoClassAdapter, ClassReader.EXPAND_FRAMES);
        //if need parameter
        if (debugPreGoClassAdapter.isNeedParameter()) {
            classWriter = new ExtendClassWriter(classLoader, ClassWriter.COMPUTE_MAXS);
            DebugClassAdapter debugClassAdapter =
                new DebugClassAdapter(classWriter, debugPreGoClassAdapter.getMethodParametersMap());
            debugClassAdapter
                .attachIncludeMethods(debugPreGoClassAdapter.getIncludes());
            classReader.accept(debugClassAdapter, ClassReader.EXPAND_FRAMES);
        }
        return classWriter.toByteArray();
    }

    @Override
    public boolean isWeavableClass(String fullQualifiedClassName) {
        boolean superResult = super.isWeavableClass(fullQualifiedClassName);
        boolean isByteCodePlugin = fullQualifiedClassName.startsWith(Constants.PLUGIN_LIBRARY);
        return superResult && !isByteCodePlugin;
    }

}
