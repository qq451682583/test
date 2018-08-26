package javassist.sample.yzk.zw.com.gradle.fragment

import javassist.sample.yzk.zw.com.gradle.Constant
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

class OnHiddenChangedAdviceAdapter extends AdviceAdapter {

    private def ownerClass

    protected OnHiddenChangedAdviceAdapter(MethodVisitor mv, int access, String name, String desc, String className) {
        super(Opcodes.ASM5, mv, access, name, desc)
        this.ownerClass = className
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode)
        addCode(mv, ownerClass)
    }

    /**
     * 为Fragment创建onHiddenChanged方法
     */
    static void createOnHiddenChanged(ClassWriter writer, String superClass, String className) {
        println "创建: " + Constant.onHiddenChanged

        MethodVisitor visitor = writer.visitMethod(ACC_PUBLIC, Constant.onHiddenChanged,
                Constant.onHiddenChangedDesc, null, null)
        visitor.visitCode()

        //调用super.onHiddenChanged方法
        visitor.visitVarInsn(ALOAD, 0)
        visitor.visitVarInsn(ILOAD, 1)
        visitor.visitMethodInsn(INVOKESPECIAL, superClass,
                Constant.onHiddenChanged, Constant.onHiddenChangedDesc, false)

        //插入fragmentStatistics.onHiddenChanged()代码;
        addCode(visitor, className)

        visitor.visitInsn(RETURN)
        visitor.visitMaxs(1, 2)
        visitor.visitEnd()
    }

    /**
     *  在Fragment回调方法onHiddenChanged中调用fragmentStatistics.onHiddenChanged()方法
     */
    private static void addCode(MethodVisitor visitor, String className) {
        visitor.visitVarInsn(ALOAD, 0)
        visitor.visitFieldInsn(GETFIELD, className,
                Constant.statisticsField, Constant.FragmentStatisticsType)
        visitor.visitVarInsn(ILOAD, 1)
        visitor.visitMethodInsn(INVOKEVIRTUAL, Constant.FragmentStatistics,
                Constant.onHiddenChanged, Constant.onHiddenChangedDesc, false)
    }
}