package com.swcourse.agent.randomtest;

import cn.hutool.core.util.RandomUtil;
import com.swcourse.agent.ByteArrayUtil;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;

/**
 * @author trotyzyq
 * @version 0.1.0
 * @create 2022-04-20 16:12
 * @since 0.1.0
 **/
public class AgentMainClass {

    public static void agentmain(String agentArgs, Instrumentation inst) throws Exception {
        inst.addTransformer(new RandomUtilClassFileTransformer(), true);
        inst.retransformClasses(RandomUtil.class);
    }

    public static void premain(String agentArgs, Instrumentation inst) throws Exception {
        String path = "/Users/zyq/project/study-project/swcourse/swcourse-swcourse1/src/main/doc/RandomUtil.class";
        byte[] bytes = ByteArrayUtil.getBytes(path);
        ClassDefinition classDefinition = new ClassDefinition(RandomUtil.class, bytes);
        inst.redefineClasses(classDefinition);
    }
}
