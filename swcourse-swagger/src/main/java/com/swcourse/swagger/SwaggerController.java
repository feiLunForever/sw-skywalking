package com.swcourse.swagger;

import com.fasterxml.classmate.ResolvedType;
import com.google.common.base.Optional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
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
import java.util.Date;
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
@Slf4j
public class SwaggerController {
    @Autowired
    private PunchRecordMapper punchRecordMapper;

    @ApiOperation("测试Get请求")
    @GetMapping("/testGet")
    public String testGet(String pageRouter, String pageDesc) {
        log.info("pageRouter:{}, pageDesc:{}", pageRouter, pageDesc);

        PunchRecordDO punchRecordDO = PunchRecordDO.builder().name("xiaoan").nickName("xiaoanNick")
                .time(new Date()).openId("39393").build();
        punchRecordMapper.insert(punchRecordDO);

        return "success";

    }

    @ApiOperation("测试Post")
    @PostMapping("/testPost")
    public String testPost(PageRouterDTO pageRouter) {
        log.info("pageRouter:{}", pageRouter);
        return "success";

    }
}
