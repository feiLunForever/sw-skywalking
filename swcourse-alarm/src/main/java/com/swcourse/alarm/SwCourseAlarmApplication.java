package com.swcourse.alarm;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SwCourseAlarmApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(SwCourseAlarmApplication.class, args);
        }catch (Exception e){
            log.error("",e);
        }
    }
}
