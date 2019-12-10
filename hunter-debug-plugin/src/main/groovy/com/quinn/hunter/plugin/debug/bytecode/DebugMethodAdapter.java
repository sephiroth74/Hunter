package com.quinn.hunter.plugin.debug.bytecode;

import com.android.build.gradle.internal.LoggerWrapper;
import com.quinn.hunter.plugin.debug.Constants;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;

import java.util.List;

/**
 * Created by Quinn on 16/09/2018.
 */
public final class DebugMethodAdapter extends LocalVariablesSorter implements Opcodes {
    @SuppressWarnings ("unused")
    private static final LoggerWrapper logger = LoggerWrapper.getLogger(DebugMethodAdapter.class);
    private final MethodDataHolder method;
    private List<Parameter> parameters;
    private String className;
    private boolean debugMethod = true;
    private boolean debugMethodWithCustomLogger = false;
    private int timingStartVarIndex;
    private String methodDesc;

    public DebugMethodAdapter(
        String className, List<Parameter> parameters, MethodDataHolder method, int access, String desc, MethodVisitor mv) {
        super(Opcodes.ASM5, access, desc, mv);
        if (!className.endsWith("/")) {
            this.className = className.substring(className.lastIndexOf("/") + 1);
        } else {
            this.className = className;
        }
        this.parameters = parameters;
        this.method = method;
        this.methodDesc = desc;
    }

    void switchToDebugImpl() {
        debugMethod = false;
        debugMethodWithCustomLogger = true;
    }

    @Override
    public void visitCode() {
        super.visitCode();
        if (!debugMethod && !debugMethodWithCustomLogger) {
            return;
        }
        int printUtilsVarIndex = newLocal(Type.getObjectType(Constants.PARAM_PRINTER_CLASS));
        mv.visitTypeInsn(NEW, Constants.PARAM_PRINTER_CLASS);
        mv.visitInsn(DUP);
        mv.visitLdcInsn(className);
        mv.visitLdcInsn(method.getName());
        mv.visitMethodInsn(
            INVOKESPECIAL, Constants.PARAM_PRINTER_CLASS, "<init>", "(Ljava/lang/String;Ljava/lang/String;)V", false);
        mv.visitVarInsn(ASTORE, printUtilsVarIndex);
        for (int i = 0; i < parameters.size(); i++) {
            Parameter parameter = parameters.get(i);
            String name = parameter.name;
            String desc = parameter.desc;
            int index = parameter.index;
            int opcode = Utils.getLoadOpcodeFromDesc(desc);
            String fullyDesc = String.format("(Ljava/lang/String;%s)L%s;", desc, Constants.PARAM_PRINTER_CLASS);
            visitPrint(printUtilsVarIndex, index, opcode, name, fullyDesc);
        }

        mv.visitVarInsn(ALOAD, printUtilsVarIndex);
        int id = newLocal(Type.INT_TYPE);
        int level = Opcodes.ICONST_0 + method.getLogLevel();
        if (level > Opcodes.ICONST_5) {
            mv.visitIntInsn(Opcodes.BIPUSH, method.getLogLevel());
        } else {
            mv.visitInsn(level);
        }

        if (debugMethod) {
            mv.visitMethodInsn(INVOKEVIRTUAL, Constants.PARAM_PRINTER_CLASS, "print", "(I)V", false);
        } else if (debugMethodWithCustomLogger) {
            mv.visitMethodInsn(INVOKEVIRTUAL, Constants.PARAM_PRINTER_CLASS, "printWithCustomLogger", "(I)V", false);
        }
        //Timing
        timingStartVarIndex = newLocal(Type.LONG_TYPE);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        mv.visitVarInsn(Opcodes.LSTORE, timingStartVarIndex);
    }

    private void visitPrint(int varIndex, int localIndex, int opcode, String name, String desc) {
        mv.visitVarInsn(ALOAD, varIndex);
        mv.visitLdcInsn(name);
        mv.visitVarInsn(opcode, localIndex);
        mv.visitMethodInsn(INVOKEVIRTUAL, Constants.PARAM_PRINTER_CLASS, "append", desc, false);
        mv.visitInsn(POP);
    }

    @Override
    public void visitInsn(int opcode) {
        if (method.isDebugOutput() && (debugMethod || debugMethodWithCustomLogger) && ((opcode >= IRETURN && opcode <= RETURN)
            || opcode == ATHROW)) {
            Type returnType = Type.getReturnType(methodDesc);
            String returnDesc = methodDesc.substring(methodDesc.indexOf(")") + 1);
            if (returnDesc.startsWith("[") || returnDesc.startsWith("L")) {
                returnDesc = "Ljava/lang/Object;"; //regard object extended from Object or array as object
            }
            //store origin return value
            int resultTempValIndex = -1;
            if (returnType != Type.VOID_TYPE || opcode == ATHROW) {
                if (opcode == ATHROW) {
                    returnType = Type.getType("Ljava/lang/Object;");
                }
                resultTempValIndex = newLocal(returnType);
                int storeOpcocde = Utils.getStoreOpcodeFromType(returnType);
                if (opcode == ATHROW) {
                    storeOpcocde = ASTORE;
                }
                mv.visitVarInsn(storeOpcocde, resultTempValIndex);
            }
            //parameter1 parameter2
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            mv.visitVarInsn(LLOAD, timingStartVarIndex);
            mv.visitInsn(LSUB);
            int index = newLocal(Type.LONG_TYPE);
            mv.visitVarInsn(LSTORE, index);

            // parameter 0 log level
            int id = newLocal(Type.INT_TYPE);
            int level = Opcodes.ICONST_0 + method.getLogLevel();
            if (level > Opcodes.ICONST_5) {
                mv.visitIntInsn(Opcodes.BIPUSH, method.getLogLevel());
            } else {
                mv.visitInsn(level);
            }

            mv.visitLdcInsn(className);    //parameter 1 string
            mv.visitLdcInsn(method.getName());   //parameter 2 string
            mv.visitVarInsn(LLOAD, index); //parameter 3 long
            //parameter 4
            if (returnType != Type.VOID_TYPE || opcode == ATHROW) {
                int loadOpcode = Utils.getLoadOpcodeFromType(returnType);
                if (opcode == ATHROW) {
                    loadOpcode = ALOAD;
                    returnDesc = "Ljava/lang/Object;";
                }
                mv.visitVarInsn(loadOpcode, resultTempValIndex);
                if (debugMethodWithCustomLogger) {
                    String formatDesc = String.format("(ILjava/lang/String;Ljava/lang/String;J%s)V", returnDesc);
                    mv.visitMethodInsn(
                        INVOKESTATIC, Constants.RESULT_PRINTER_CLASS, "printWithCustomLogger", formatDesc, false);
                } else {
                    String formatDesc = String.format("(ILjava/lang/String;Ljava/lang/String;J%s)V", returnDesc);
                    mv.visitMethodInsn(INVOKESTATIC, Constants.RESULT_PRINTER_CLASS, "print", formatDesc, false);
                }
                mv.visitVarInsn(loadOpcode, resultTempValIndex);
            } else {
                mv.visitLdcInsn("void");
                if (debugMethodWithCustomLogger) {
                    mv.visitMethodInsn(
                        INVOKESTATIC, Constants.RESULT_PRINTER_CLASS, "printWithCustomLogger",
                        "(ILjava/lang/String;Ljava/lang/String;JLjava/lang/Object;)V", false
                    );
                } else {
                    mv.visitMethodInsn(
                        INVOKESTATIC, Constants.RESULT_PRINTER_CLASS, "print",
                        "(ILjava/lang/String;Ljava/lang/String;JLjava/lang/Object;)V", false
                    );
                }
            }
        }
        super.visitInsn(opcode);
    }

}
