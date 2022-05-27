package com.swcourse.bytebuddy.test;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @author zhangyuqiang
 * @version 1.0.0
 * @des 0.1.0
 * @create 2022-05-10 17:41
 **/
public class AnimalWaterInterceptor {

    @RuntimeType
    public static Object eat(@Origin Method method, @SuperCall Callable<?> callable) throws Exception {
        System.out.println("add the water before eat");
        Object object = callable.call(); // 执行原函数
        System.out.println("add the water after eat");
        return object;
    }
}
