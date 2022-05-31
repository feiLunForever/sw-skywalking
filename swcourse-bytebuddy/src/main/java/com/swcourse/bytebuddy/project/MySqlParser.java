package com.swcourse.bytebuddy.project;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


/**
 * @description：
 * @author: zhanglao
 * @date: 2022/5/13 3:27 下午
 * @Desc: MySQL的解析Parser
 */
@Slf4j
public class MySqlParser  {


    public void doParse(String sql, List<SqlField> sqlFields) {
        String lowerSQL = sql.toLowerCase(Locale.ROOT);
        if (lowerSQL.startsWith("insert")) {
            doInsert(sql, sqlFields);
        }

        if (lowerSQL.startsWith("select")) {
            doSelect(sql, sqlFields);
        }
    }

    /**
     * 解析、加密处理
     * @param sqlFields
     * @param columnList
     */
    private void doOperate(List<SqlField> sqlFields, List<TableStat.Column> columnList) {
        if (columnList.size() != sqlFields.size()) {
            log.error("解析SQL异常,param values 长度和解析条件长度不一致");
            return;
        }

        //表名、参数名填充
        for (int i = 0; i < columnList.size(); i++) {
            TableStat.Column column = columnList.get(i);
            SqlField sqlField = sqlFields.get(i);
            sqlField.setFieldName(column.getName());
            sqlField.setTableName(column.getTable());
        }
    }


    /**
     * select语句逻辑执行
     * @param sql
     * @param sqlFields
     */
    private void doSelect(String sql, List<SqlField> sqlFields) {
        List<TableStat.Column> columnList = new ArrayList<>();
        // 通过druid解析SQL转为 Statement
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, "mysql");
        for (SQLStatement item : stmtList) {
            MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
            item.accept(visitor);
            // 获取SQL中条件信息
            List<TableStat.Condition> conditionList = visitor.getConditions();
            // 过滤condition为空的值
            conditionList = conditionList.stream().filter(x -> !CollectionUtils.isEmpty(x.getValues())).collect(Collectors.toList());
            columnList.addAll(conditionList.stream().map(TableStat.Condition::getColumn).collect(Collectors.toList()));
        }
        doOperate(sqlFields, columnList);

    }


    /**
     * insert语句逻辑执行
     * @param sql
     * @param sqlFields
     */
    private void doInsert(String sql, List<SqlField> sqlFields) {
        List<TableStat.Column> columnList = new ArrayList<>();
        // 通过druid解析SQL转为 Statement
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, "mysql");

        for (SQLStatement item : stmtList) {
            MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
            item.accept(visitor);
            // 获取SQL中插入信息
            Collection<TableStat.Column> columns = visitor.getColumns();
            if (!CollectionUtils.isEmpty(columns)) {
                columnList.addAll(columns);
                continue;
            }
        }
        doOperate(sqlFields, columnList);
    }

}
