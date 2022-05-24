package com.swcourse.bytebuddy;

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
public class CreateMethodInterceptor {
    public  String dinners(String value) {
        System.out.println("创建方法Case的入参为:" + value);
        return "我是创建方法Case的实现";
    }
}
