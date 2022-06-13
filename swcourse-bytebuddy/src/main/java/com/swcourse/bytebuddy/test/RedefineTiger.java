package com.swcourse.bytebuddy.test;

/**
 * @author zhangyuqiang
 * @version 1.0.0
 * @des 0.1.0
 * @create 2022-05-10 17:39
 **/
public class RedefineTiger {
    public String eat(Integer date, String food) {
        System.out.println(date + " redefineTiger is eat " + food);
        return date + " tiger is eat";
    }

}
