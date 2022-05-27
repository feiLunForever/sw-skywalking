package com.swcourse.bytebuddy.project;

import com.mysql.jdbc.ByteArrayRow;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetImpl;
import com.mysql.jdbc.RowDataStatic;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.Super;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

/**
 * @author zhangyuqiang
 * @version 1.0.0
 * @des MySQL的拦截器，逻辑业务处理
 * @create 2022-05-10 17:41
 **/
@Slf4j
public class Mysql5Interceptor {

    @RuntimeType
    public void intercept(@SuperCall Callable<?> callable, @Super Object object) throws Exception {
        before(object);
        Object result = callable.call();
        after(object);
    }


    /**
     * before 处理
     *
     * @param object
     * @throws Exception
     */
    public void before(Object object) throws Exception {
        //获取 PreparedStatement
        Statement statement = getStatement(object);

        String sql = getFinalSQL(statement).toLowerCase(Locale.ROOT);
        ;

        // 获取参数
        byte[][] parameterValues = getParameterValues(statement);

        if (sql.startsWith("select")) {
            // 模拟加密之后的字节数组
            parameterValues[0] = setEncryptValue(parameterValues[0]);
        }
        if (sql.startsWith("insert")) {
            // 模拟加密之后的字节数组
            parameterValues[3] = setEncryptValue(parameterValues[3]);
        }
    }


    /**
     * 设置加密字段
     */
    private byte[] setEncryptValue(byte[] originBytes) {
        Base64EncryptAlgorithmStrategy encryptAlgorithmStrategy = new Base64EncryptAlgorithmStrategy();
        // 获取去除引号后的数组
        byte[] removeQuotationBytes = removeQuotationBytes(originBytes);

        byte[] encryptBytes =  encryptAlgorithmStrategy.encrypt(removeQuotationBytes, "123456");;

        // 构建新字节数组
        byte[] finalByte = new byte[encryptBytes.length + 2];
        if (finalByte.length - 2 >= 0) {
            finalByte[0] = originBytes[0];
            finalByte[finalByte.length - 1] = originBytes[originBytes.length - 1];
            System.arraycopy(encryptBytes, 0, finalByte, 1, finalByte.length - 2);
        }
        return finalByte;
    }

    /**
     * 移除字符两端的单引号
     *
     * @param source
     * @return
     */
    private byte[] removeQuotationBytes(byte[] source) {
        byte[] target = new byte[source.length - 2];
        if (target.length >= 0) {
            System.arraycopy(source, 1, target, 0, target.length);
        }
        return target;
    }


    /**
     * 获取PreparedStatement
     *
     * @param object
     * @return
     * @throws Exception
     */
    public Statement getStatement(Object object) throws Exception {
        Field field = object.getClass().getDeclaredField("target");
        field.setAccessible(true);
        return (Statement) field.get(object);
    }

    /**
     * 获取字节参数
     *
     * @param statement
     * @return
     * @throws Exception
     */
    public byte[][] getParameterValues(Statement statement) throws Exception {
        Field paramValueField = PreparedStatement.class.getDeclaredField("parameterValues");
        paramValueField.setAccessible(true);
        return (byte[][]) paramValueField.get(statement);
    }


    public String getFinalSQL(Statement statement) throws Exception {
        return ((PreparedStatement) statement).asSql();
    }


    private void after(Object object) throws Exception {
        Base64EncryptAlgorithmStrategy base64EncryptAlgorithmStrategy = new Base64EncryptAlgorithmStrategy();
        // 获取statement
        Statement statement = getStatement(object);

        // 如果不是查询，不做任何处理
        String lowerSql = getFinalSQL(statement).toLowerCase(Locale.ROOT);
        if (!lowerSql.startsWith("select")) {
            return;
        }

        List<ResultColumn> resultColumnList = buildResultColumn(statement);
        List<ResultRow> resultRowList = buildResultRow(statement);


        for (ResultRow row : resultRowList) {
            //获取参数值
            byte[][] rowsValue = row.getRowsValue();
            if (null == rowsValue || 0 == rowsValue.length) {
                continue;
            }
            for (int j = 0; j < rowsValue.length; j++) {
                ResultColumn resultColumn = resultColumnList.get(j);
                // 获取每一个字段的值加密处理
                byte[] bytes = rowsValue[j];
                if (bytes == null || bytes.length == 0) {
                    continue;
                }
                if (!resultColumn.getName().equals("name")) {
                    continue;
                }

                rowsValue[j] = base64EncryptAlgorithmStrategy.decrypt(bytes,"123456");
            }
        }
        decryptList(statement, resultRowList);
    }

    public List<ResultColumn> buildResultColumn(Statement statement) throws Exception {
        ResultSetImpl resultSet = (ResultSetImpl) statement.getResultSet();
        Field fieldRowData = ResultSetImpl.class.getDeclaredField("rowData");
        fieldRowData.setAccessible(true);
        RowDataStatic rowDataStatic = (RowDataStatic) fieldRowData.get(resultSet);

        Field metadata = RowDataStatic.class.getDeclaredField("metadata");
        metadata.setAccessible(true);
        com.mysql.jdbc.Field[] fields = (com.mysql.jdbc.Field[]) metadata.get(rowDataStatic);

        List<ResultColumn> resultColumnList = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            com.mysql.jdbc.Field field = fields[i];
            ResultColumn resultColumn = ResultColumn.builder()
                    .tableName(field.getTableName())
                    .originalTableName(field.getOriginalTableName())
                    .name(field.getName())
                    .originalName(field.getOriginalName()).build();
            resultColumnList.add(resultColumn);
        }
        return resultColumnList;
    }

    public List<ResultRow> buildResultRow(Statement statement) throws Exception {
        ResultSetImpl resultSet = (ResultSetImpl) statement.getResultSet();
        Field fieldRowData = ResultSetImpl.class.getDeclaredField("rowData");
        fieldRowData.setAccessible(true);
        RowDataStatic rowDataStatic = (RowDataStatic) fieldRowData.get(resultSet);

        //此处获取 ByteArrayRow 为 ResultSetRow 子类，目的为下面获得参数数组 internalRowData
        Field rowDataMetadata = RowDataStatic.class.getDeclaredField("rows");
        rowDataMetadata.setAccessible(true);
        List<ByteArrayRow> byteArrayRowList = (List<ByteArrayRow>) rowDataMetadata.get(rowDataStatic);

        List<ResultRow> resultRowList = new ArrayList<>();
        for (ByteArrayRow byteArrayRow : byteArrayRowList) {
            Field internalRowData = ByteArrayRow.class.getDeclaredField("internalRowData");
            internalRowData.setAccessible(true);
            byte[][] bytes = (byte[][]) internalRowData.get(byteArrayRow);
            resultRowList.add(ResultRow.builder().rowsValue(bytes).build());
        }
        return resultRowList;
    }

    public void decryptList(Statement statement, List<ResultRow> resultRowList) throws Exception {
        ResultSetImpl resultSet = (ResultSetImpl) statement.getResultSet();
        Field fieldRowData = ResultSetImpl.class.getDeclaredField("rowData");
        fieldRowData.setAccessible(true);
        RowDataStatic rowDataStatic = (RowDataStatic) fieldRowData.get(resultSet);

        //此处获取 ByteArrayRow 为 ResultSetRow 子类，目的为下面获得参数数组 internalRowData
        Field rowDataMetadata = RowDataStatic.class.getDeclaredField("rows");
        rowDataMetadata.setAccessible(true);
        List<ByteArrayRow> byteArrayRowList = (List<ByteArrayRow>) rowDataMetadata.get(rowDataStatic);

        for (int i = 0; i < resultRowList.size(); i++) {
            ResultRow resultRow = resultRowList.get(i);
            ByteArrayRow byteArrayRow = byteArrayRowList.get(i);
            byte[][] bytes = resultRow.getRowsValue();
            for (int j = 0; j < bytes.length; j++) {
                byteArrayRow.setColumnValue(j, bytes[j]);
            }
        }
    }
}
