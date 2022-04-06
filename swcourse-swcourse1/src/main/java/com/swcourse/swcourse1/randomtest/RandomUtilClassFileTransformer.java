package com.swcourse.swcourse1.randomtest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class RandomUtilClassFileTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
        ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (!"cn/hutool/core/util/RandomUtil".equals(className)){
            return null;
        }

        return getBytesFromFile("/Users/zyq/project/study-project/swcourse/swcourse-swcourse1/src/main/doc/RandomUtil.class");
    }

    public static byte[] getBytesFromFile(String fileName) {
        try {
            // precondition
            File file = new File(fileName);
            InputStream is = new FileInputStream(file);
            long length = file.length();
            byte[] bytes = new byte[(int) length];

            // Read in the bytes
            int offset = 0;
            int numRead = 0;
            while (offset <bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }

            if (offset < bytes.length) {
                throw new IOException("Could not completely read file "
                    + file.getName());
            }
            is.close();
            return bytes;
        } catch (Exception e) {
            System.out.println("error occurs in _ClassTransformer!"
                + e.getClass().getName());
            return null;
        }
    }
}
