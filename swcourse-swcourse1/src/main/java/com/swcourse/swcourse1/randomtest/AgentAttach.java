package com.swcourse.swcourse1.randomtest;

import cn.hutool.core.util.RandomUtil;

import java.lang.instrument.Instrumentation;

public class AgentAttach {
    public static void agentmain(String agentArgs, Instrumentation inst) throws Exception {
        inst.addTransformer(new RandomUtilClassFileTransformer(), true);
        inst.retransformClasses(RandomUtil.class);
        System.out.println("Agent Main Done");
    }
}