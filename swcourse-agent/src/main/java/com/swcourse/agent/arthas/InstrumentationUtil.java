package com.swcourse.agent.arthas;

import com.swcourse.agent.randomtest.RandomClassFileTransformer;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * @author your name or email
 * @version 0.1.0
 * @create 2022-04-24 11:31
 * @since 0.1.0
 **/
public class InstrumentationUtil {
    public static Instrumentation instrumentation;

    /**
     * 根据入参进行操作
     */
    public static void handle(String[] args) throws UnmodifiableClassException {
        String methodName = args[0];
        if("removeTransformer".equalsIgnoreCase(methodName)){
            instrumentation.removeTransformer(new RandomClassFileTransformer());
            instrumentation.retransformClasses();
        }
    }

}
