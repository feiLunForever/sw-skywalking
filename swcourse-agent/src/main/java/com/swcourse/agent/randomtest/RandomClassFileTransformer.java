package com.swcourse.agent.randomtest;

import com.swcourse.agent.ByteArrayUtil;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class RandomClassFileTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
        ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (!"cn/hutool/core/util/RandomUtil".equals(className)){
            return null;
        }

        return ByteArrayUtil.getBytes("/Users/zyq/project/study-project/swcourse/swcourse-agent/src/main/doc/RandomUtil.class");
    }
}
