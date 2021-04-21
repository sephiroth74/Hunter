package com.quinn.hunter.plugin.debug;

import com.android.build.api.transform.Context;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.LoggerWrapper;
import com.quinn.hunter.plugin.debug.bytecode.DebugWeaver;
import com.quinn.hunter.transform.HunterTransform;
import com.quinn.hunter.transform.RunVariant;

import org.gradle.api.Project;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by Quinn on 16/09/2018.
 */
public class DebugHunterTransform extends HunterTransform {
    private static final LoggerWrapper logger = LoggerWrapper.getLogger(DebugHunterTransform.class);

    private Project project;
    private DebugHunterExtension debugHunterExtension;

    public DebugHunterTransform(Project project) {
        super(project);
        this.project = project;
        project.getExtensions().create("debugHunterExt", DebugHunterExtension.class);
        this.bytecodeWeaver = new DebugWeaver();
        System.out.println("DebugHunterTransform(" + project.getName() + ")");
    }

    @Override
    public void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        debugHunterExtension = (DebugHunterExtension) project.getExtensions().getByName("debugHunterExt");
        System.out.println(String.format("transform. debugResult: %s, logLevel: %s, debugArguments: %s", debugHunterExtension.debugResult, debugHunterExtension.logLevel, debugHunterExtension.debugArguments));

        Constants.DEBUG_RESULT = debugHunterExtension.debugResult;
        Constants.LOG_LEVEL = debugHunterExtension.logLevel;
        Constants.DEBUG_ARGUMENTS = debugHunterExtension.debugArguments;

        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental);
    }

    @Override
    protected RunVariant getRunVariant() {
        return debugHunterExtension.runVariant;
    }

    @Override
    protected boolean inDuplicatedClassSafeMode() {
        return debugHunterExtension.duplcatedClassSafeMode;
    }

    @Override
    protected boolean getDebugResult() {
        return debugHunterExtension.debugResult;
    }

    @Override
    protected boolean getDebugArguments() {
        return debugHunterExtension.debugArguments;
    }

    @Override
    protected int getLogLevel() {
        return debugHunterExtension.logLevel;
    }

}