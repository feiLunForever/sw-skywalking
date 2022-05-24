package com.swcourse.bytebuddy;

import com.swcourse.bytebuddy.bytebuddy.AgentCallInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * @author your name or email
 * @version 0.1.0
 * @create 2022-02-15 17:29
 * @since 0.1.0
 **/
@RestController
@Slf4j
public class ByteBuddyController {




    @GetMapping(value = "/test_byteBuddy2")
    public void test_byteBuddy2() throws Exception {
        Instrumentation instrumentation = ByteBuddyAgent.install();


        AgentBuilder.Transformer transformer =
                (builder, typeDescription, classLoader, module) -> {
                    //method()指定哪些方法需要被拦截，ElementMatchers.any()表//示拦截所有方法
                    return builder.method(named("execute"))
//                            .or(named("executeQuery"))
//                            .or(named("executeUpdate")))
                            //intercept()指明拦截上述方法的拦截器
                            .intercept(MethodDelegation.to(AgentCallInterceptor.class));
                };
        //ByteBuddy专门有个AgentBuilder来处理JavaAgent的场景
        new AgentBuilder.Default()
                //根据包名前缀拦截类
                .type(named("com.mysql.jdbc.JDBC42PreparedStatement")
                )
//                .type(ElementMatchers.nameContains("com.mysql.jdbc.PreparedStatement"))
                //拦截到的类由transformer处理
                .transform(transformer)
                .installOn(instrumentation);

    }

}
