package com.swcourse.springcloud.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author your name or email
 * @version 0.1.0
 * @create 2022-02-15 17:29
 * @since 0.1.0
 **/
@RestController
@Slf4j
public class ConsumerController {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private  RestTemplate restTemplate;



    @GetMapping(value = "/echo/{str}")
    public String echo(@PathVariable String str) throws InterruptedException {
        log.info("开始调用日志");
        return restTemplate.getForObject("http://provider/echo/" + str, String.class);
    }
}
