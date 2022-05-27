package com.swcourse.bytebuddy.test;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.Test;

import java.lang.reflect.Modifier;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * @author zhangyuqiang
 * @version 1.0.0
 * @des 章御强
 * @create 2022-05-10 17:41
 **/
public class ByteBuddyTest {
    /**
     * 创建类
     */
    @Test
    public void testCreateClass() throws Exception {
        new ByteBuddy().subclass(BaseTiger.class).make()
                .load(this.getClass().getClassLoader()).getLoaded()
                .newInstance().eat("2022");
    }

    /**
     * 创建方法(使用静态和非静态)
     */
    @Test
    public void testCreateMethod() throws Exception {
        Class clazz = new ByteBuddy().subclass(BaseTiger.class)
                .defineMethod("dinner", String.class, Modifier.PUBLIC)
                .withParameters(String.class)
//                .intercept(FixedValue.value("111"))
                .intercept(MethodDelegation.to(CreateMethodInterceptor.class))
//                .intercept(MethodDelegation.to(new CreateMethodInterceptor()))
                .make().load(this.getClass().getClassLoader()).getLoaded();
        Object value = clazz.getMethod("dinner", String.class).invoke(clazz.newInstance(), "2022");
        System.out.println(value);
    }

    // 生成类并且添加拦截，需要静态方法
    // 拦截方法
    @Test
    public void testAddInterceptor() throws Exception {
        new ByteBuddy().subclass(BaseTiger.class)
//                .method(named("eat").and(named("run")))
//                .method(ElementMatchers.isPublic())
//                .method(ElementMatchers.is(ElementMatchers.returns(String.class)))
                .method(ElementMatchers.takesArgument(0, String.class))
//                .intercept(FixedValue.value("返回值"))
                .intercept(MethodDelegation.to(AnimalWaterInterceptor.class))
                .make().load(this.getClass().getClassLoader())
                .getLoaded().newInstance().eat("2022");
    }

    @Test
    public void redefineClass1() throws Exception {
        ByteBuddyAgent.install();
        new ByteBuddy().redefine(BaseTiger.class)
                .method(named("eat"))
                .intercept(FixedValue.value("Hello Foo Redefined"))
                .make()
                .load(this.getClass().getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
        BaseTiger baseTiger = new BaseTiger();
        System.out.println(baseTiger.eat("222"));
    }


    @Test
    public void redefineClass2() throws Exception {
        ByteBuddyAgent.install();
        RedefineTiger redefineTiger = new RedefineTiger();
        new ByteBuddy().redefine(BaseTiger.class)
                .name(redefineTiger.getClass().getName())
                .make()
                .load(this.getClass().getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
        System.out.println(redefineTiger.eat("2022"));
    }
}
