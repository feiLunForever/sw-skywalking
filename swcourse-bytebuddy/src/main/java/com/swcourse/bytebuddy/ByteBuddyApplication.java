package com.swcourse.bytebuddy;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ByteBuddyApplication {

  public static void main(String[] args) {
    try {
      SpringApplication.run(ByteBuddyApplication.class, args);

    }catch (Exception e){
        log.error("", e);
    }
  }
}
