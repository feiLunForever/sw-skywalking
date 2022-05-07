package com.swcourse.springcloud.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ImageBanner;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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
@RefreshScope
@Slf4j
public class ProviderController {

    @Autowired
    private PunchRecordMapper punchRecordMapper;


    @GetMapping(value = "/echo/{string}")
    public String echo(@PathVariable String string) {
        log.info("我正在打印日志");

        PunchRecordDO punchRecordDO = PunchRecordDO.builder().name("111").build();
        punchRecordMapper.insert(punchRecordDO);

        System.out.println(ImageBanner.class.getClassLoader());
        return "Hello Nacos Discovery " + string;
    }

}
