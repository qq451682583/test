package javassist.sample.yzk.zw.com.gradle.click

import javassist.sample.yzk.zw.com.gradle.Constant
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class ClickListenerVisitor extends ClassVisitor {

    private def className
    private def superName
    private def implementsFromOnClickListener = false

    ClickListenerVisitor(ClassWriter writer) {
        super(Opcodes.ASM5, writer)
    }

    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces)
        this.className = name
        this.superName = superName
        if (interfaces != null && interfaces.contains(Constant.OnClickListener)) {
            implementsFromOnClickListener = true
        }
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions)
        if (implementsFromOnClickListener) {
            if (name == Constant.onClick) {
                println "modify ClickListener " + className.replace("/", ".") + " onClick"
                return new OnClickVisitor(mv)
            }
            return mv
        }
        return mv

    }
}