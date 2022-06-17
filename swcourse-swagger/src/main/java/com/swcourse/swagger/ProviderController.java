package com.swcourse.swagger;

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
public class ProviderController {

    @Autowired
    private PunchRecordMapper punchRecordMapper;


    @GetMapping(value = "/echo/{string}")
    public String echo(@PathVariable String string) {
        PunchRecordDO punchRecordDO = PunchRecordDO.builder().name("111").build();
        punchRecordMapper.insert(punchRecordDO);
        return "Hello Nacos Discovery " + string;
    }

}
