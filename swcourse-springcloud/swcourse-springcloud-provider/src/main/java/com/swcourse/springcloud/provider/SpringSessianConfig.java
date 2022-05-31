package com.swcourse.springcloud.provider;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author
 * @description
 **/
@Configuration
@EnableRedisHttpSession(redisNamespace = "session", maxInactiveIntervalInSeconds = 60 * 60 * 24)
public class SpringSessianConfig {
}
