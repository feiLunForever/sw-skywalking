package com.swcourse.logback;

import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author your name or email
 * @version 0.1.0
 * @since 0.1.0
 **/
@RestController
@Slf4j
@RequestMapping("/logback")
public class LogController {


    @GetMapping(value = "/echo/{string}")
    public Result echo(@PathVariable String string) {
        log.info(string);
        log.info("当前 TraceId 为{}:", TraceContext.traceId());
        Result result = new Result();
        result.setData(1);
        return result;
    }

    @GetMapping(value = "/grpc")
    public Result grpc() {
        log.info("当前GRPC-Reporter TraceId 为{}:", TraceContext.traceId());
        Result result = new Result();
        result.setData(1);
        return result;
    }


}
