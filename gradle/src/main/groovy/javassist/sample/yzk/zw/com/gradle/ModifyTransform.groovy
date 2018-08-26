package javassist.sample.yzk.zw.com.gradle

import com.android.SdkConstants
import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import javassist.sample.yzk.zw.com.gradle.click.ClickListenerVisitor
import javassist.sample.yzk.zw.com.gradle.fragment.FragmentVisitor
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils

import javax.xml.crypto.dsig.TransformException

class ModifyTransform extends Transform {

    @Override
    String getName() {
        return "ModifyTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)

        transformInvocation.inputs.each {
            it.jarInputs.each {
                if(it && it.file && it.file.exists()) {
                    // 重命名输出文件（同目录copyFile会冲突）
                    def jarName = it.name
                    def md5Name = DigestUtils.md5Hex(it.file.getAbsolutePath())
                    if (jarName.endsWith(".jar")) {
                        jarName = jarName.substring(0, jarName.length() - 4)
                    }
                    def dest = transformInvocation.outputProvider.getContentLocation(
                            DigestUtils.md5Hex(jarName + md5Name), it.contentTypes, it.scopes, Format.JAR)

                    FileUtils.copyFile(it.file, dest)
                }
            }

//            input.directoryInputs.each {
//                DirectoryInput directoryInput ->
//                    //处理完输入文件之后，要把输出给下一个任务
//                    def dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name,
//                            directoryInput.contentTypes, directoryInput.scopes,
//                            Format.DIRECTORY)
//                    FileUtils.copyDirectory(directoryInput.file, dest)
//            }


            it.directoryInputs.each {
                scanFile(it.file)
                // 获取output目录
                def dest = transformInvocation.outputProvider.getContentLocation(
                        it.name,
                        it.contentTypes,
                        it.scopes,
                        Format.DIRECTORY)

                println "copy directory: " + it.file.absolutePath
                // 将input的目录复制到output指定目录
                FileUtils.copyDirectory(it.file, dest)
            }
        }
    }

    private void scanFile(File file) {
        if(file.isDirectory()) {
            file.eachFileRecurse {
                modify(it)
            }
        }
    }

    private void modify(File className) {
        def filePath = className.absolutePath
        if (!filePath.endsWith(SdkConstants.DOT_CLASS) || filePath.contains('R$')
                || filePath.contains('R.class') || filePath.contains("BuildConfig.class")) {
            return
        }
        modifyFragment(className)
        modifyClickListener(className)
    }

    //利用ASM修改Fragment
    private void modifyFragment(File file) {
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES)
        FragmentVisitor visitor = new FragmentVisitor(writer)
        ClassReader reader = new ClassReader(file.getBytes())
        reader.accept(visitor, ClassReader.EXPAND_FRAMES)

        wirteFile(file, writer.toByteArray())
    }

    //修改OnClickedListener
    private void modifyClickListener(File file) {
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES)
        ClickListenerVisitor visitor = new ClickListenerVisitor(writer)
        ClassReader reader = new ClassReader(file.getBytes())
        reader.accept(visitor, ClassReader.EXPAND_FRAMES)

        wirteFile(file, writer.toByteArray())
    }

    //将修改后的class文件保存
    private void wirteFile(File file, byte[] bytes) {
        FileOutputStream fos = new FileOutputStream(file)
        try {
            fos.write(bytes)
            fos.close()
        } finally {
            fos.close()
        }
    }
}