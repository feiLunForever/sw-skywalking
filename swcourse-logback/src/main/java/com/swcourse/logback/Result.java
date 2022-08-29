package com.swcourse.logback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhangyuqiang
 * @version 1.0.0
 * @des 0.1.0
 * @create 2022-08-26 15:10
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private boolean success;
    private Object data;
    private String errorCode;
    private String errorMsg;
    private String traceId;
}
