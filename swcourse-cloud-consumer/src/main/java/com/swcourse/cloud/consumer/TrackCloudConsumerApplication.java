package com.swcourse.cloud.consumer;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
public class TrackCloudConsumerApplication {

  public static void main(String[] args) {
    SpringApplication.run(TrackCloudConsumerApplication.class, args);
  }

  @LoadBalanced
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }


  @RestController
  public class TestController {

    private final RestTemplate restTemplate;

    @Autowired
    public TestController(RestTemplate restTemplate) {this.restTemplate = restTemplate;}

    @GetMapping(value = "/echo/{str}")
    public String echo(@PathVariable String str) throws InterruptedException {
      log.info("开始调用日志");
      Thread.sleep(1500);
      return restTemplate.getForObject("http://provider/echo/" + str, String.class);
    }
  }
}
