package com.swcourse.agent.arthas;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.swcourse.agent.ByteArrayUtil;
import com.swcourse.agent.randomtest.RandomClassFileTransformer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zyq
 * @version 0.1.0
 * @create 2022-04-24 14:56
 * @since 0.1.0
 **/
public class ArthasMain {
    public static Instrumentation instrumentation;
    public static Map<String, ClassFileTransformer> transformerMap = new HashMap<>();
    public static Map<String, Class> classMap = new HashMap<>();

    public static void agentmain(String agentArgs, Instrumentation inst) throws Exception {
        // 为了保证 每次调用时的 Instrumentation都是一致，所以赋值到静态变量
        if (instrumentation == null) {
            instrumentation = inst;
        }
        // 将原先的class 保存到map中，用于回滚时使用
        Class[] classes = inst.getAllLoadedClasses();
        for (Class classZ : classes) {
            classMap.put(classZ.getName(), classZ);
        }
        // 判断入参的方法名称做处理
        String[] array = StrUtil.split(agentArgs, ",");
        String methodName = array[0], classFilePath = array[1];
        if ("retransformClasses".equals(methodName)) {
            retransformClasses(classFilePath);
        }
        if ("removeTransformer".equals(methodName)) {
            removeTransformer(classFilePath);
        }
    }

    /**
     * 进行retransformClasses 类转换
     * @param filePath
     * @throws Exception
     */
    public static void retransformClasses(String filePath) throws Exception {
        ClassFileTransformer classFileTransformer = new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                if (!"cn/hutool/core/util/RandomUtil".equals(className)) {
                    return null;
                }
                return ByteArrayUtil.getBytes(filePath);
            }
        };
        instrumentation.addTransformer(classFileTransformer, true);
        // 保存classFileTransformer 在移除时使用
        transformerMap.put(filePath, classFileTransformer);

        String fileName = getName(filePath);
        for (String className : classMap.keySet()) {
            if (className.endsWith(fileName)) {
                Class classz = classMap.get(className);
                instrumentation.retransformClasses(classz);
                break;
            }
        }
        System.out.println("修改后返回值" + RandomUtil.simpleUUID());
    }

    /**
     * 移除 transformer
     * @param filePath
     * @throws UnmodifiableClassException
     */
    public static void removeTransformer(String filePath) throws UnmodifiableClassException {
        ClassFileTransformer classFileTransformer = transformerMap.get(filePath);
        instrumentation.removeTransformer(classFileTransformer);
        String fileName = getName(filePath);
        for (String className : classMap.keySet()) {
            if (className.endsWith(fileName)) {
                // 重新加载用于回滚
                Class clazz = classMap.get(className);
                instrumentation.retransformClasses(clazz);
                break;
            }
        }
        System.out.println("回滚后返回值" + RandomUtil.simpleUUID());
    }

    /**
     * 通过入参获取调用的方法名
     * @param filePath
     * @return
     */
    private static String getName(String filePath) {
        String[] array = StrUtil.split(filePath, "/");
        String[] classArray = array[array.length - 1].split("\\.");
        return classArray[0];
    }

}
