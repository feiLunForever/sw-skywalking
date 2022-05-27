package com.swcourse.bytebuddy.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangyuqiang
 * @version 1.0.0
 * @des 0.1.0
 * @create 2022-05-24 15:06
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultColumn {
    private String tableName;

    private String originalTableName;

    private String originalName;

    private String name;
}
