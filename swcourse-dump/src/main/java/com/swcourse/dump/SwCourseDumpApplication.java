package com.swcourse.dump;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SwCourseDumpApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(SwCourseDumpApplication.class, args);
        }catch (Exception e){
            log.error("",e);
        }
    }
}
