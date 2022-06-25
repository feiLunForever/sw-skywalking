package com.swcourse.springcloud.provider;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
public class ProviderApplication {

  public static void main(String[] args) {
    try {
      SpringApplication.run(ProviderApplication.class, args);

    }catch (Exception e){
        log.error("", e);
    }
  }
}
