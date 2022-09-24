package com.swcourse.dump;

import com.sun.org.apache.bcel.internal.classfile.Unknown;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Native;

/**
 * @author your name or email
 * @version 0.1.0
 * @since 0.1.0
 **/
@RestController
@Slf4j
@RequestMapping("/dump")
public class DumpController {
    @Autowired
    private PunchRecordMapper punchRecordMapper;


    @GetMapping(value = "/testDump")
    public String testDump() throws InterruptedException {
        log.info("测试告警指标:{}", "testServiceRespTimeRule");
        PunchRecordDO punchRecordDO = PunchRecordDO.builder().name("111").build();
        punchRecordMapper.insert(punchRecordDO);
        Thread.sleep(3200);
        return "success";
    }
}
