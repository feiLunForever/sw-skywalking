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

    public static void premain(String agentArgs, Instrumentation inst) throws Exception {
        System.out.println("最初返回值:" + RandomUtil.simpleUUID());
        String path = "/Users/zyq/project/study-project/swcourse/swcourse-agent/src/main/doc/RandomUtil.class";
        byte[] bytes = ByteArrayUtil.getBytes(path);
        ClassDefinition classDefinition = new ClassDefinition(RandomUtil.class, bytes);
        inst.redefineClasses(classDefinition);

        System.out.println("修改后返回值:" + RandomUtil.simpleUUID());
        String rollBackPath = "/Users/zyq/project/study-project/swcourse/swcourse-agent/src/main/doc/RollBackRandomUtil.class";
        byte[] rollbackBytes = ByteArrayUtil.getBytes(rollBackPath);
        ClassDefinition classDefinition2 = new ClassDefinition(RandomUtil.class, rollbackBytes);
        inst.redefineClasses(classDefinition2);
        System.out.println("回滚后返回值:" + RandomUtil.simpleUUID());
    }
}
