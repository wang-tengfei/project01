package com.example.config.config;

import io.opentracing.Tracer;
import io.opentracing.contrib.elasticsearch.common.TracingHttpClientConfigCallback;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Tengfei.Wang
 * @Description;
 * @Date: Created in 下午6:05 13/11/18
 * @Modified by:
 */
@Configuration
@Slf4j
public class RestClientConfig {

    @Value("${spring.elasticsearch.jest.uris}")
    public String esHost;


    @Bean
    public RestClient restClient(Tracer tracer) {
        return RestClient.builder(
                new HttpHost("localhost", 9200))
                .setHttpClientConfigCallback(new TracingHttpClientConfigCallback(tracer))
                .build();
    }


}
