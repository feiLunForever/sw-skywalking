package com.swcourse.bytebuddy.test;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CreateMethodInterceptor {
    public static String dinners(Integer date, String value) {
        return "我是创建方法Case dinners 的 Integer string 实现";
    }

    public String noStaticDinners(Integer date, String value) {
        return "我是创建方法Case no static 的 Integer string 实现";
    }

    public static String dinner2(String value, Integer date) {
        return "我是创建方法Case dinner2 的 Integer  实现";
    }

    public static int dinner3(Integer date, String value) {
        return 333;
    }

    public static String dinner4(Integer date, String value, Integer other) {
        return "我是创建方法Case dinner4 的 Integer  实现";
    }

    public static String dinner3(String date) {
        return "我是创建方法Case dinner2 的 Integer  实现";
    }

}
