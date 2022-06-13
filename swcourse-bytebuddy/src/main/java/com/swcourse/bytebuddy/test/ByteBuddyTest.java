package com.swcourse.bytebuddy.test;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.Morph;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.Test;

import java.lang.reflect.Field;
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
                .newInstance().eat(2022, "apple");
    }

    @Test
    public void testCreateMethodFixedValue() throws Exception {
        Class clazz = new ByteBuddy().subclass(BaseTiger.class)
                .defineMethod("dinner", String.class, Modifier.PUBLIC)
                .withParameters(Integer.class, String.class)
                .intercept(FixedValue.value("my return value"))
                .make().load(this.getClass().getClassLoader()).getLoaded();
        Object value = clazz.getMethod("dinner", Integer.class, String.class)
                .invoke(clazz.newInstance(), 2022, "apple");
        System.out.println(value);
    }

    @Test
    public void testCreateMethodInterceptor() throws Exception {
        Class clazz = new ByteBuddy().subclass(BaseTiger.class)
                .defineMethod("dinner", String.class, Modifier.PUBLIC)
                .withParameters(Integer.class, String.class)
                .intercept(MethodDelegation.to(CreateMethodInterceptor.class))
//                .intercept(MethodDelegation.to(new CreateMethodInterceptor()))
                .make().load(this.getClass().getClassLoader()).getLoaded();
        Object value = clazz.getMethod("dinner", Integer.class, String.class)
                .invoke(clazz.newInstance(), 2022, "apple");
        System.out.println(value);
    }


    @Test
    public void testAddInterceptor() throws Exception {
        Object value = new ByteBuddy().subclass(BaseTiger.class)
                .method(named("eat").or(named("run")))
//                .method(ElementMatchers.isPublic())
//                .method(ElementMatchers.is(ElementMatchers.returns(String.class)))
//                .method(ElementMatchers.takesArgument(0, String.class))
//                .intercept(FixedValue.value("返回值"))
                .intercept(MethodDelegation.to(AnimalWaterInterceptor.class))
                .make().load(this.getClass().getClassLoader())
                .getLoaded().newInstance().eat(2022, "apple");
    }

    @Test
    public void testUpdateArgumentsInterceptor() throws Exception {
        Object value = new ByteBuddy().subclass(BaseTiger.class)
                .method(named("eat").or(named("run")))
                .intercept(MethodDelegation.withDefaultConfiguration()
                        .withBinders(Morph.Binder.install(OverrideCallable.class))
                        .to( UpdateArgumentsInterceptor.class))
                .make().load(this.getClass().getClassLoader())
                .getLoaded().newInstance().eat(2022, "apple");
    }

    @Test
    public void redefineFixedValue() throws Exception {
        ByteBuddyAgent.install();
        new ByteBuddy().redefine(BaseTiger.class)
                .method(named("eat"))
                .intercept(FixedValue.value("Hello Foo Redefined"))
                .make()
                .load(this.getClass().getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
        BaseTiger baseTiger = new BaseTiger();
        System.out.println(baseTiger.eat(2022, "apple"));
    }


    @Test
    public void redefineClass() throws Exception {
        ByteBuddyAgent.install();
        BaseTiger baseTiger = new BaseTiger();
        baseTiger.eat(2022, "apple");
        new ByteBuddy().redefine(RedefineTiger.class)
                .name(baseTiger.getClass().getName())
                .make()
                .load(this.getClass().getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
        baseTiger.eat(2022, "apple");
    }

}
