package javassist.sample.yzk.zw.com.gradle.fragment

import javassist.sample.yzk.zw.com.gradle.Constant
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

class OnDestroyAdviceAdapter extends AdviceAdapter {

    private def ownerClass

    protected OnDestroyAdviceAdapter(MethodVisitor mv, int access, String name, String desc, String className) {
        super(Opcodes.ASM5, mv, access, name, desc)
        this.ownerClass = className
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode)
        addCode(mv, ownerClass)
    }

    /**
     * 为Fragment创建onDestroyView方法
     */
    static void createOnDestroyView(ClassWriter writer, String superClass, String className) {
        println "创建: " + Constant.onDestroyView

        MethodVisitor visitor = writer.visitMethod(ACC_PUBLIC, Constant.onDestroyView,
                Constant.onDestroyViewDesc, null, null)
        visitor.visitCode()

        //调用super.onDestroyView方法
        visitor.visitVarInsn(ALOAD, 0)
        visitor.visitMethodInsn(INVOKESPECIAL, superClass,
                Constant.onDestroyView, Constant.onDestroyViewDesc, false)

        //插入fragmentStatistics.onDestroyView()代码;
        addCode(visitor, className)

        visitor.visitInsn(RETURN)
        visitor.visitMaxs(1, 1)
        visitor.visitEnd()
    }

    /**
     *  在Fragment回调方法onDestroyView中调用fragmentStatistics.onDestroyView()方法
     */
    private static void addCode(MethodVisitor visitor, String className) {
        visitor.visitVarInsn(ALOAD, 0)
        visitor.visitFieldInsn(GETFIELD, className,
                Constant.statisticsField, Constant.FragmentStatisticsType)
        visitor.visitMethodInsn(INVOKEVIRTUAL, Constant.FragmentStatistics,
                Constant.onDestroyView, Constant.onDestroyViewDesc, false)
    }
}