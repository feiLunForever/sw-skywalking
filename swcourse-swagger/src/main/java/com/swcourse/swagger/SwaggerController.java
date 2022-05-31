package com.swcourse.swagger;

import com.fasterxml.classmate.ResolvedType;
import com.google.common.base.Optional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.NameValueExpression;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import springfox.documentation.RequestHandler;
import springfox.documentation.RequestHandlerKey;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spring.web.WebMvcRequestHandler;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
//        WebMvcRequestHandler webMvcRequestHandler = new WebMvcRequestHandler();
//        // 类: webMvcRequestHandler.getHandlerMethod().getBeanType().getName();
//        // Tag:
//        Annotation[] annotations =  webMvcRequestHandler.getHandlerMethod().getBeanType().getAnnotations();
//        Api api = null;
//        String[] tags = api.tags();
//
//        // apiOperation
//        Annotation[] annotations2 =  webMvcRequestHandler.getHandlerMethod().getMethod().getAnnotations();
//        ApiOperation apiOperation = null;
//        String value = apiOperation.value();
//
//        // 路径
//        new ArrayList<>(webMvcRequestHandler.getRequestMapping().getPatternsCondition().getPatterns()).get(0);
        return "success";

    }
}
