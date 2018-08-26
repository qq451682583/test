package javassist.sample.yzk.zw.com.gradle.fragment

import javassist.sample.yzk.zw.com.gradle.Constant
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes


class FragmentVisitor extends ClassVisitor {

    private def className
    private def superName
    private def methodMap = new HashMap<String, Boolean>()
    private def needChange = false

    FragmentVisitor(ClassWriter writer) {
        super(Opcodes.ASM5, writer)
    }

    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces)
        this.className = name
        this.superName = superName
        needChange = superName == Constant.FragmentV4

        if (needChange) {
            println "modify fragment : " + name.replace("/", ".")
            addStatisticsField()
        }
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions)
        if (!needChange()) {
            return mv
        }
        if (name == Constant.constructor) {
            println "修改构造函数"
            return new ConstructAdviceAdapter(mv, access, name, desc, className)
        }
        else if (name == Constant.onCreateView) {
            //如果存在onCreateView则修改它，不存在就要创建这个方法
            println "修改 onCreateView"
            methodMap.put(Constant.onCreateView, true)
            return new OnCreateViewAdviceAdapter(mv, access, name, desc, className)
        } else if (name == Constant.onHiddenChanged) {
            //如果存在onHiddenChanged则修改它，不存在就创建这个方法
            println "修改 onHiddenChanged"
            methodMap.put(Constant.onHiddenChanged, true)
            return new OnHiddenChangedAdviceAdapter(mv, access, name, desc, className)
        } else if (name == Constant.onResume) {
            //如果存在onResume方法则修改它，不存在就创建这个方法
            println "修改 onResume"
            methodMap.put(Constant.onResume, true)
            return new OnResumeAdviceAdapter(mv, access, name, desc, className)
        } else if (name == Constant.onPause) {
            //如果存在onPause方法则修改它，不存在就创建这个方法
            println "修改 onPause"
            methodMap.put(Constant.onPause, true)
            return new OnPauseAdviceAdapter(mv, access, name, desc, className)
        } else if (name == Constant.onDestroyView) {
            //如果存在onDestroyView方法，则修改它，不存在就创建这个方法
            println "修改 onDestroyView"
            methodMap.put(Constant.onDestroyView, true)
            return new OnDestroyAdviceAdapter(mv, access, name, desc, className)
        } else if (name == Constant.setUserVisibleHint) {
            //如果存在setUserVisibleHint方法，则修改它，不存在就创建这个方法
            println "修改 setUserVisibleHint"
            methodMap.put(Constant.setUserVisibleHint, true)
            return new SetUserVisibleHintAdviceAdapter(mv, access, name, desc, className)
        }
        else {
            return mv
        }
    }

    @Override
    void visitEnd() {
        super.visitEnd()
        if (!needChange()) {
            return
        }
        //没有修改的方法说明不存在，需要创建
        if (methodMap.get(Constant.onCreateView) == null) {
            OnCreateViewAdviceAdapter.createOnCreateView(cv, superName, className)
        }
        if (methodMap.get(Constant.onHiddenChanged) == null) {
            OnHiddenChangedAdviceAdapter.createOnHiddenChanged(cv, superName, className)
        }
        if (methodMap.get(Constant.onResume) == null) {
            OnResumeAdviceAdapter.createOnResume(cv, superName, className)
        }
        if (methodMap.get(Constant.onPause) == null) {
            OnPauseAdviceAdapter.createOnPause(cv, superName, className)
        }
        if (methodMap.get(Constant.onDestroyView) == null) {
            OnDestroyAdviceAdapter.createOnDestroyView(cv, superName, className)
        }
        if (methodMap.get(Constant.setUserVisibleHint) == null) {
            SetUserVisibleHintAdviceAdapter.createSetUserVisibleHint(cv, superName, className)
        }
    }

    /**
     * 添加一个fragmentStatistics字段
     */
    private void addStatisticsField() {
        FieldVisitor fv = cv.visitField(Opcodes.ACC_PRIVATE, Constant.statisticsField,
                Constant.FragmentStatisticsType, null, null)
        if (fv != null) {
            fv.visitEnd()
        }
    }

    /**
     * 判断当前类是否是继承v4包Fragment，是则需要修改
     * @return
     */
    private boolean needChange() {
        return needChange
    }
}