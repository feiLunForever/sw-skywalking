package com.swcourse.bytebuddy.project;

import lombok.Data;

/**
 * @description：
 * @author: zhanglao
 * @date: 2022/5/15 10:15 下午
 */
@Data
public class SqlField {
    /**
     * 参数
     */
    private byte[] fieldValue;
    /**
     * String形式参数
     */
    private String fieldStrValue;
    /**
     * 参数类型
     */
    private int fieldType;

    /**
     * 是否加密
     */
    private Boolean ifEncrypt;

    /**
     * 参数
     */
    private byte[] originFieldValue;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 字段名称
     */
    private String fieldName;
}
