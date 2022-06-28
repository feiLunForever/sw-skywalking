package com.swcourse.dubbo.consumer;

import com.swcourse.dubbo.common.DubboService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
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

    @DubboReference
    private DubboService dubboService;

    @GetMapping(value = "/consume")
    public String echo() throws InterruptedException {
        dubboService.helloDubbo("hello dubbo");
//        dubboService.helloDubbo("hello dubbo");
        return "success";
    }
}
