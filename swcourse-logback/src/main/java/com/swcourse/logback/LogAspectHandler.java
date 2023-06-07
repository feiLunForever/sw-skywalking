package com.swcourse.logback;


import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


/**
 * @Author zyq
 * @Description controller日志handler
 * @Date 2019/8/22 16:36
 */

@Aspect
@Slf4j
@Component
public class LogAspectHandler {


    @Pointcut("within(@(org.springframework.stereotype.Controller || org.springframework.web.bind.annotation.RestController) *)")
    private void allMethod() {
    }

    @Around("allMethod()")
    public Object doAround(ProceedingJoinPoint call) throws Throwable {
        Signature signature = call.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        Result result = (Result) call.proceed();
        result.setTraceId(TraceContext.traceId());
        return result;
    }
}
