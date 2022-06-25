package com.swcourse.dubbo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author your name or email
 * @version 0.1.0
 * @create 2022-02-15 17:29
 * @since 0.1.0
 **/
@RestController
@Slf4j
public class ConsumerController {


    @GetMapping(value = "/echo/{str}")
    public String echo(@PathVariable String str) throws InterruptedException {
        log.info("开始调用日志");
        return "success";
    }
}
