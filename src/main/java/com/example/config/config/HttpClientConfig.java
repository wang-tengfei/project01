package com.example.config.config;

import io.opentracing.contrib.apache.http.client.TracingHttpClientBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Tengfei Wang
 * @description: 配置httpclient 使其支持jaeger
 * @date: Created in 15:43 2019-05-14
 * @modified by:
 */
@Configuration
public class HttpClientConfig {

    @Bean
    public CloseableHttpClient httpClient(){
        return new TracingHttpClientBuilder().build();
    }
}
