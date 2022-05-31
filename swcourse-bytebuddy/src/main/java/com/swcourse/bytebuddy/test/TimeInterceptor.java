package com.swcourse.bytebuddy.test;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @author your name or email
 * @version 0.1.0
 * @create 2022-03-22 11:33
 * @since 0.1.0
 **/
public class TimeInterceptor {
    @RuntimeType
    public static Object intercept(@Origin Method method,
                                   @SuperCall Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();
        System.out.println(method.getName() + ":"
                + (System.currentTimeMillis() - start) + "ms");
        return callable.call(); // 执行原函数

    }
}
