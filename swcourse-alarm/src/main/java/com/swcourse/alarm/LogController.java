package com.swcourse.alarm;

import lombok.extern.slf4j.Slf4j;
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
    public String echo(@PathVariable String string) {
        return null;
    }
}
