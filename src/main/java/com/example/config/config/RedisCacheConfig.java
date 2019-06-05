package com.example.config.config;

import io.opentracing.Tracer;
import io.opentracing.contrib.redis.spring.data.connection.TracingRedisConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * @author: Tengfei Wang
 * @description:   配置redis 使其支持jaeger
 * @date: Created in 14:45 2019-05-14
 * @modified by:
 */
@Configuration
public class RedisCacheConfig {

    /**
     * Create tracing connection factory bean
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory(Tracer tracer) {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
        lettuceConnectionFactory.afterPropertiesSet();
        return new TracingRedisConnectionFactory(lettuceConnectionFactory,true, tracer);
    }
}
