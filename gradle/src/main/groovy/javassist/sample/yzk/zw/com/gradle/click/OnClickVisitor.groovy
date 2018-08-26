package javassist.sample.yzk.zw.com.gradle.click

import javassist.sample.yzk.zw.com.gradle.Constant
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class OnClickVisitor extends MethodVisitor implements Opcodes{

    OnClickVisitor(MethodVisitor mv) {
        super(Opcodes.ASM5, mv)
    }

    @Override
    void visitCode() {
        super.visitCode()

        mv.visitTypeInsn(NEW, Constant.ClickStatistics)
        mv.visitInsn(DUP)
        mv.visitVarInsn(ALOAD, 1)
        mv.visitMethodInsn(INVOKESPECIAL, Constant.ClickStatistics,
                Constant.constructor, Constant.ClickStatisticsDesc, false)
        mv.visitVarInsn(ASTORE, 2)


    }

    @Override
    void visitInsn(int opcode) {
        if(opcode >= IRETURN && opcode <= RETURN) {
            mv.visitVarInsn(ALOAD, 2)
            mv.visitMethodInsn(INVOKEVIRTUAL, Constant.ClickStatistics,
                    Constant.clickEnd, Constant.clickEndDesc, false)
        }
        super.visitInsn(opcode)
    }
}