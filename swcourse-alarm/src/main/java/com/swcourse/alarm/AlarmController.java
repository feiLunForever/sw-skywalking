package com.swcourse.alarm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author your name or email
 * @version 0.1.0
 * @since 0.1.0
 **/
@RestController
@Slf4j
@RequestMapping("/alarm")
public class AlarmController {
    @Autowired
    private PunchRecordMapper punchRecordMapper;

    @GetMapping(value = "/testServiceRespTimeRule")
    public String testServiceRespTimeRule() throws InterruptedException {
        log.info("测试告警指标:{}", "testServiceRespTimeRule");
        Thread.sleep(3200);
        return "success";
    }

    @PostMapping(value = "/testReceiveWebHook")
    public String testReceiveWebHook(@RequestBody List<WebHookParamDTO> webHookParamDTO) throws InterruptedException {
        return "success";
    }

    @GetMapping(value = "/testDump")
    public String testDump() throws InterruptedException {
        log.info("测试告警指标:{}", "testServiceRespTimeRule");
        PunchRecordDO punchRecordDO = PunchRecordDO.builder().name("111").build();
        punchRecordMapper.insert(punchRecordDO);
        Thread.sleep(3200);
        return "success";
    }
}
