package com.swcourse.bytebuddy.bytebuddy;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.util.concurrent.Callable;

/**
 * @author zhangyuqiang
 * @version 1.0.0
 * @des 0.1.0
 * @create 2022-05-10 17:41
 **/
public class RedefineWaterInterceptor {

    @RuntimeType
    public String eat(String date) {
        System.out.println(date + "tiger is eat");
        return date + "tiger is eat";
    }
}
