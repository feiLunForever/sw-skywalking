package com.swcourse.swcourse1.randomtest;

import cn.hutool.core.util.RandomUtil;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class AgentPremain {


    public static void premain(String agentArgs, Instrumentation inst) throws Exception {
        inst.addTransformer(new RandomUtilClassFileTransformer(), true);
        inst.retransformClasses(RandomUtil.class);
        System.out.println("Agent Main Done");
    }
}