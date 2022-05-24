package com.swcourse.bytebuddy;

import com.swcourse.bytebuddy.bytebuddy.AgentCallInterceptor;
import com.swcourse.bytebuddy.bytebuddy.AnimalWaterInterceptor;
import com.swcourse.bytebuddy.bytebuddy.Cat;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;
import org.junit.Test;

import java.lang.instrument.Instrumentation;
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
    public void testAgentAddInterceptor() throws Exception {
        Instrumentation instrumentation = ByteBuddyAgent.install();

        AgentBuilder.Transformer transformer =
                new AgentBuilder.Transformer() {
                    @Override
                    public DynamicType.Builder<?> transform(
                            DynamicType.Builder<?> builder,
                            TypeDescription typeDescription,
                            ClassLoader classLoader,
                            JavaModule module) {
                        //method()指定哪些方法需要被拦截，ElementMatchers.any()表//示拦截所有方法
                        return builder.method(ElementMatchers.named("eat"))
                                //intercept()指明拦截上述方法的拦截器
                                .intercept(MethodDelegation.to(AgentCallInterceptor.class));
                    }
                };
        //ByteBuddy专门有个AgentBuilder来处理JavaAgent的场景
        new AgentBuilder.Default()
                //根据包名前缀拦截类
                .type(ElementMatchers.nameStartsWith("com.swcourse.bytebuddy"))
                //拦截到的类由transformer处理
                .transform(transformer)
                .installOn(instrumentation);

        BaseTiger baseTiger = new BaseTiger();
        baseTiger.eat("2022");

        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseTiger baseTiger = new BaseTiger();
                baseTiger.eat("2022");
            }
        }).start();

    }

    // 重定义方法的返回值
    @Test
    public void testRedefine() throws Exception {
        ByteBuddyAgent.install();
        new ByteBuddy()
                .redefine(BaseTiger.class)
                .method(named("eat"))
                .intercept(FixedValue.value("Hello Foo Redefined"))
                .make()
                .load(this.getClass().getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
        BaseTiger baseTiger = new BaseTiger();
        System.out.println(baseTiger.eat("2022"));
    }

    @Test
    public void testRedefine2() throws Exception {
        BaseTiger baseTiger2 = new BaseTiger();
        Cat cat = new Cat();
        ByteBuddyAgent.install();
        // 重定义的redfine class 是主类
        // 如果试图redifine已经加载的类，会抛出异常
        new ByteBuddy()
                .redefine(Cat.class)
                .name(BaseTiger.class.getName())
                .make()
                .load(this.getClass().getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
        BaseTiger baseTiger = new BaseTiger();
        System.out.println(baseTiger.eat("2022"));
    }
}
