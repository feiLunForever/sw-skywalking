package com.swcourse.swagger;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author your name or email
 * @version 0.1.0
 * @create 2022-04-07 17:37
 * @since 0.1.0
 **/
@RestController
@RequestMapping("/swagger")
@Api(tags = "swagger测试")
public class SwaggerController {

    @ApiOperation("测试1")
    @GetMapping("/test")
    public String test(){
        return "success";
    }
}
