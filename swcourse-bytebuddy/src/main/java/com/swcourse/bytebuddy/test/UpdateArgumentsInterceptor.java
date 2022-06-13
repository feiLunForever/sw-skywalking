package com.swcourse.bytebuddy.test;

import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @author zhangyuqiang
 * @version 1.0.0
 * @des 0.1.0
 * @create 2022-05-10 17:41
 **/
public class UpdateArgumentsInterceptor {
    @RuntimeType
    public static Object eat(@Origin Method method, @Morph OverrideCallable callable,
                             @AllArguments Object[] allArguments) throws Exception {
        System.out.println("update argument before eat");
        allArguments[1] = "updateApple";
        // 执行原函数
        Object object = callable.call(allArguments);
        System.out.println("update argument after eat");
        return object;
    }
}
