package com.swcourse.test.swcourse1;

import cn.hutool.core.util.RandomUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
        System.out.println(SwCourse1Controller.class.getClassLoader());

        return RandomUtil.simpleUUID();
    }

    @GetMapping("/attachTest/{pid}")
    public String premainTest(@PathVariable String pid) throws Exception{
        VirtualMachine vmObj = null;
        try {
            vmObj = VirtualMachine.attach(pid);
            if (vmObj != null) {
                vmObj.loadAgent("/Users/zyq/project/study-project/swcourse/swcourse-agent" +
                        "/target/swcourse-agent-1.0.0-SNAPSHOT-jar-with-dependencies.jar=removeTransformer," +
                        "/Users/zyq/project/study-project/swcourse/swcourse-agent/src/main/doc/RandomUtil.class", null);
            }
        } finally {
            if (null != vmObj) {
                vmObj.detach();
            }
        }
        return "success";
    }
}
