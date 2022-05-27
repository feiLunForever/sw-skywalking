package com.swcourse.bytebuddy.project;


/**
 * @author zhangyuqiang
 * @version 1.0.0
 * @des MySQL的插桩配置类，用于定义加强类、拦截类、拦截方法
 * @create 2022-05-12 10:49
 **/
public class Mysql5Instrumentation {


    public String getBlurJarName() {
        return "mysql-connector-java-5";
    }

    public String getEnhanceClass() {
        return "com.mysql.jdbc.JDBC42PreparedStatement";
    }

    public String getEnhanceMethod() {
        return "execute";
    }

    public String getInterceptor() {
        return "com.szzj.common.encrypt.mysql5.Mysql5Interceptor";
    }
}
