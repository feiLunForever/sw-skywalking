package com.swcourse.bytebuddy.test;

/**
 * @author zhangyuqiang
 * @version 1.0.0
 * @des 0.1.0
 * @create 2022-05-10 17:39
 **/
public class BaseTiger {
    public String eat(String date) {
        System.out.println(date + " tiger is eat");
        return date + " tiger is eat";
    }

    public String run(String date) {
        System.out.println(date + " tiger is run");
        return date + " tiger is run";
    }
}
