package javassist.sample.yzk.zw.com.gradle.fragment

import javassist.sample.yzk.zw.com.gradle.Constant
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.Opcodes

/**
 * 修改FragmentOnCreateView方法
 */
class OnCreateViewAdviceAdapter extends AdviceAdapter {

    private String ownerClass

    protected OnCreateViewAdviceAdapter(MethodVisitor mv, int access, String name, String desc, String className) {
        super(Opcodes.ASM5, mv, access, name, desc)
        this.ownerClass = className
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode)
        //增加this.fragmentStatistics.onCreateView(this)
        addOnCreateViewCode(mv, ownerClass)
    }
/**
     * 创建OnCreateView方法
     */
    static void createOnCreateView(ClassWriter writer, String superClass, String className) {
        println "创建: " + Constant.onCreateView

        //创建onCreateView
        MethodVisitor visitor = writer.visitMethod(ACC_PUBLIC,
                Constant.onCreateView, Constant.onCreateViewDesc, null, null)
        visitor.visitCode()

        //增加this.fragmentStatistics.onCreateView(this)
        addOnCreateViewCode(visitor, className)

        //调用super.onCreateView
        visitor.visitVarInsn(ALOAD, 0)
        visitor.visitVarInsn(ALOAD, 1)
        visitor.visitVarInsn(ALOAD, 2)
        visitor.visitVarInsn(ALOAD, 3)
        visitor.visitMethodInsn(INVOKESPECIAL, superClass,
                Constant.onCreateView, Constant.onCreateViewDesc, false)
        visitor.visitInsn(IRETURN)

        visitor.visitMaxs(1, 4)
        visitor.visitEnd()
    }

    /**
     * 给onCreateView添加this.fragmentStatistics.onCreateView(this)代码
     */
    private static void addOnCreateViewCode(MethodVisitor visitor, String className) {
        visitor.visitVarInsn(ALOAD, 0)
        visitor.visitFieldInsn(GETFIELD, className, Constant.statisticsField,
                Constant.FragmentStatisticsType)
        visitor.visitVarInsn(ALOAD, 0)
        visitor.visitMethodInsn(INVOKEVIRTUAL, Constant.FragmentStatistics,
                Constant.onCreateView, Constant.FragmentParamDesc, false)

    }
}