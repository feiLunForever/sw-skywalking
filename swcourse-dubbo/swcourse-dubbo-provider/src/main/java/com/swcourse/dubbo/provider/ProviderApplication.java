package com.swcourse.dubbo.provider;


import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@EnableDubbo
public class ProviderApplication {

  public static void main(String[] args) {
    try {
      SpringApplication.run(ProviderApplication.class, args);

    }catch (Exception e){
        log.error("", e);
    }
  }
}
