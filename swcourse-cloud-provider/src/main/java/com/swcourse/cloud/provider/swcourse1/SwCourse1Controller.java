package com.swcourse.cloud.provider.swcourse1;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.hutool.core.util.RandomUtil;
/**
 * @author your name or email
 * @version 0.1.0
 * @create 2022-04-06 16:12
 * @since 0.1.0
 **/
@RestController
@RequestMapping("/swcourse1")
public class SwCourse1Controller {

    @GetMapping("/premainTest")
    public String premainTest(){
        return RandomUtil
    }
}
