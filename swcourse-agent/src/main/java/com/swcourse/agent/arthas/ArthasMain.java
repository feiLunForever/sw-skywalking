package com.swcourse.agent.arthas;

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
        if (instrumentation == null) {
            instrumentation = inst;
        }
        Class[] classes = inst.getAllLoadedClasses();
        for (Class classZ : classes) {
            classMap.put(classZ.getName(), classZ);
        }

        String[] array = StrUtil.split(agentArgs, ",");
        String methodName = array[0], classFilePath = array[1];
        if ("retransformClasses".equals(methodName)) {
            retransformClasses(classFilePath);
        }
        if ("removeTransformer".equals(methodName)) {
            removeTransformer(classFilePath);
        }
    }


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
        transformerMap.put(filePath, classFileTransformer);

        String fileName = getName(filePath);
        for (String className : classMap.keySet()) {
            if (className.endsWith(fileName)) {
                Class classz = classMap.get(className);
                instrumentation.retransformClasses(classz);
                break;
            }
        }

    }


    public static void removeTransformer(String filePath) throws UnmodifiableClassException {
        ClassFileTransformer classFileTransformer = transformerMap.get(filePath);
        instrumentation.removeTransformer(classFileTransformer);
        String fileName = getName(filePath);
        for (String className : classMap.keySet()) {
            if (className.endsWith(fileName)) {
                Class classz = classMap.get(className);
                instrumentation.retransformClasses(classz);
                break;
            }
        }
    }


    private static String getName(String filePath) {
        String[] array = StrUtil.split(filePath, "/");
        String[] classArray = array[array.length - 1].split("\\.");
        return classArray[0];
    }

}
