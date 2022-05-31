package com.swcourse.bytebuddy.project;

import com.sun.deploy.util.JarUtil;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.utility.JavaModule;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.instrument.Instrumentation;
import java.util.Iterator;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * @author zhangyuqiang
 * @version 1.0.0
 * @des 核心类：用于开关、扫描、启动、拦截的 整体流程。非常重要！！！
 * @create 2022-05-12 10:47
 **/
@Slf4j
public class EncryptLauncher implements InitializingBean {


    @Override
    public void afterPropertiesSet() throws Exception {
        Instrumentation instrumentation = ByteBuddyAgent.install();
        new AgentBuilder.Default()
                // 拦截的方法和拦截器
                .type(buildEnhanceClassMatch())
                // 需要拦截的类
                .transform(new Transformer())
                // redefine 策略
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .installOn(instrumentation);

    }


    /**
     * 构建enhance match
     *
     * @return
     */
    private ElementMatcher buildEnhanceClassMatch() {
        Mysql5Instrumentation mysql5Instrumentation = new Mysql5Instrumentation();
        String enhanceClass = mysql5Instrumentation.getEnhanceClass();
        ElementMatcher.Junction judge = named(enhanceClass);
        return judge;
    }


    /**
     * 构建transformer
     */
    private class Transformer implements AgentBuilder.Transformer {

        @Override
        public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
                                                final TypeDescription typeDescription,
                                                final ClassLoader classLoader,
                                                final JavaModule module) {
            Mysql5Instrumentation mysql5Instrumentation = new Mysql5Instrumentation();
            Mysql5Interceptor mysql5Interceptor = new Mysql5Interceptor();
            // 设置拦截器
            builder = builder.method(named(mysql5Instrumentation.getEnhanceMethod()))
                    .intercept(MethodDelegation.to(mysql5Interceptor));
            return builder;
        }
    }
}
