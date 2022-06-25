package com.swcourse.dubbo.provider;

import com.swcourse.dubbo.common.DubboService;

/**
 * @author your name or email
 * @version 0.1.0
 * @create 2022-06-25 21:44
 * @since 0.1.0
 **/
@org.apache.dubbo.config.annotation.DubboService
public class DubboServiceImpl implements DubboService {
    @Override
    public String helloDubbo(String msg) {
        System.out.printf(msg);
        return "success";
    }
}
