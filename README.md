# test
利用asm修改字节，在fragment生命周期中插入统计事件，在onClick函数中插入点击事件

在使用ASM的时候最好对能熟悉java字节码，如果不太清楚可以使用AS插件jclasslib bytecode viewer可以将java代码转化为字节码，这样就能
先把代码用java写好然后转化为字节码对照着ASM写

个人遇到的几个坑
1、在动态添加成员变量的时候传入的类型是java类名（xx.xx.xx），结果是编译的时候.class文件能够生成也不报错但是打包到dex一直失败，应该使用类路径（xx/xx/xx）
2、使用AdviceAdapter处理方法时onMethodEnter和onMethodExist对局部变量并不能共享，也就是说不能再onMethodEnter方法里面创建局部变量然后在onMethodExist方法里面使用这个局部变量。如果这么做了也是.class文件能够生成但是打包到dex一直失败
3、在调用MethodVisitor.visitMethodIn时闯入的类型是java类型，结果是编译.class文件能够生成，apk也能够生成但是安装之后会报类找不到，应该使用类路径（xx/xx/xx）

原java文件和修改后的.class文件对比
![BaseFragment](https://github.com/yaozhukuang/test/blob/master/caputrue/basefragment.png)
![Fragment4](https://github.com/yaozhukuang/test/blob/master/caputrue/fragment4.png)
