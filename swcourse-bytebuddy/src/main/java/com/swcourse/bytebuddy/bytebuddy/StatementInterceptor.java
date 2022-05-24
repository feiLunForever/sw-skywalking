package com.swcourse.bytebuddy.bytebuddy;

import com.mysql.jdbc.ConnectionImpl;
import com.mysql.jdbc.JDBC42PreparedStatement;
import com.mysql.jdbc.JDBC42ResultSet;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.Callable;

/**
 * @author zhangyuqiang
 * @version 1.0.0
 * @des 0.1.0
 * @create 2022-05-10 17:41
 **/
public class StatementInterceptor {


    @RuntimeType
    public static Object intercept(int age,
            @AllArguments Object[] args,
            @SuperCall Callable<?> callable, @Super Object object) throws Exception {
        args[0] = ResultSet.TYPE_SCROLL_SENSITIVE;
        args[1] = ResultSet.CONCUR_UPDATABLE;
        Object result = callable.call();
        System.out.println(result);

        return result;
    }


}
