package com.swcourse.swcourse1.bytebuddyAgentTest;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

public class AgentMethodCost {
    public static void premain(String agentArgs, Instrumentation inst) {
        //ByteBuddy会根据Transformer指定的规则进行拦截并增强代码
        AgentBuilder.Transformer transformer =
                new AgentBuilder.Transformer() {
                    @Override
                    public DynamicType.Builder<?> transform(
                            DynamicType.Builder<?> builder,
                            TypeDescription typeDescription,
                            ClassLoader classLoader,
                            JavaModule module) {
                        //method()指定哪些方法需要被拦截，ElementMatchers.any()表//示拦截所有方法
                        return builder.method(ElementMatchers.<MethodDescription>any())
                        //intercept()指明拦截上述方法的拦截器
                        .intercept(MethodDelegation.to(TimeInterceptor.class));
                    }
                };
        //ByteBuddy专门有个AgentBuilder来处理JavaAgent的场景
        new AgentBuilder.Default()
                //根据包名前缀拦截类
                .type(ElementMatchers.nameStartsWith("com.swcourse"))
                //拦截到的类由transformer处理
                .transform(transformer)
                .installOn(inst);//安装到Instrumentation
    }

}