package javassist.sample.yzk.zw.com.gradle.fragment

import javassist.sample.yzk.zw.com.gradle.Constant

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

/**
 * 修改Fragment构造函数添加统计字段
 */
class ConstructAdviceAdapter extends AdviceAdapter {

    private String ownerClass

    protected ConstructAdviceAdapter(MethodVisitor mv, int access, String name, String desc, String className) {
        super(Opcodes.ASM5, mv, access, name, desc)
        this.ownerClass = className
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode)

        mv.visitVarInsn(ALOAD, 0)
        mv.visitTypeInsn(NEW, Constant.FragmentStatistics)
        mv.visitInsn(DUP)
        mv.visitMethodInsn(INVOKESPECIAL, Constant.FragmentStatistics,
                Constant.constructor, "()V", false)

        mv.visitFieldInsn(PUTFIELD, ownerClass, Constant.statisticsField, Constant.FragmentStatisticsType)
        mv.visitInsn(RETURN)
    }

}