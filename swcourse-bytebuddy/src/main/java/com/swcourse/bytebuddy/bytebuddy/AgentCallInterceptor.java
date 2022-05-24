package com.swcourse.bytebuddy.bytebuddy;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.util.JdbcConstants;
import com.mysql.jdbc.*;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * @author zhangyuqiang
 * @version 1.0.0
 * @des 0.1.0
 * @create 2022-05-10 17:41
 **/
public class AgentCallInterceptor {

    public static void before(@Origin String method, @AllArguments Object[] args, @Super Object object) throws Exception {
        System.out.println(object);
        Field field = object.getClass().getDeclaredField("target");
        field.setAccessible(true);
        JDBC42PreparedStatement statement = (JDBC42PreparedStatement) field.get(object);
        String sql = "select a.b from punch_record where id =29 and name = '111' and id = '333' and id in (1,2,3)";
//        String sql = statement.getPreparedSql();
        List<SQLStatement> sqlStatements = SQLUtils.parseStatements(sql, JdbcConstants.MYSQL);


        for (SQLStatement sqlStatement : sqlStatements) {
            SchemaStatVisitor schemaStatVisitor = SQLUtils.createSchemaStatVisitor(JdbcConstants.MYSQL);
            sqlStatement.accept(schemaStatVisitor);
            Map<TableStat.Name, TableStat> tables = schemaStatVisitor.getTables();
            Collection<TableStat.Column> columns = schemaStatVisitor.getColumns();
            System.out.println(columns);
        }

    }

    public static void after(@Origin String method, @AllArguments Object[] args, @Super Object object, Object result) throws Exception {
        Field field = object.getClass().getDeclaredField("target");
        field.setAccessible(true);
        JDBC42PreparedStatement statement = (JDBC42PreparedStatement) field.get(object);



        ResultSetImpl resultSet = (ResultSetImpl) statement.getResultSet();
        Field fieldRowData = ResultSetImpl.class.getDeclaredField("rowData");
        fieldRowData.setAccessible(true);
        RowDataStatic rowData =(RowDataStatic) fieldRowData.get(resultSet);
        ResultSetRow resultSetRow = rowData.getAt(0);
        byte[] bytes = resultSetRow.getColumnValue(3);
        if (bytes != null && bytes.length > 0) {
            String origValue = StringUtils.toString(bytes, "UTF-8");
            resultSetRow.setColumnValue(3,"xxxx".getBytes(StandardCharsets.UTF_8));

        }
    }


    @RuntimeType
    public static void intercept(@Origin String method, @AllArguments Object[] args,
                                 @SuperCall Callable<?> callable, @Super Object object) throws Exception {


        before(method, args, object);
        Object result = callable.call();
        after(method, args, object, result);
    }


}
