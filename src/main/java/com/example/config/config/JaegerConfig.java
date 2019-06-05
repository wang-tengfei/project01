package com.example.config.config;

import io.opentracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Tengfei Wang
 * @escription
 * @date Created in 15:27 2019-05-13
 * @modified by
 */
@Configuration
@Slf4j
public class JaegerConfig {

    @Value("${opentracing.jaeger.udp-sender.host}")
    private String hostName;

    @Value("${opentracing.jaeger.udp-sender.port}")
    private Integer port;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${opentracing.jaeger.log-spans}")
    private boolean logSpans;

    /**
     * 采集策略 全样采集
     * ProbabilisticSampler probabilistic 概率采集，默认万份之一
     * RateLimitingSampler ratelimiting 限速采集，每秒只能采集一定量的数据
     * RemotelyControlledSampler 动态采集策略，根据当前系统的访问量调节采集策略 暂无
     */
    private static final String SAMPLER_CONST = "const";

/*    @Bean
    public Tracer jaegerTracer() {

        io.jaegertracing.Configuration.SenderConfiguration sender = new io.jaegertracing.Configuration.SenderConfiguration();
        sender.withAgentHost(hostName)
                .withAgentPort(port);

        io.jaegertracing.Configuration config = new io.jaegertracing.Configuration(appName);
        config.withReporter(new io.jaegertracing.Configuration.ReporterConfiguration().withSender(sender).withFlushInterval(100).withLogSpans(logSpans))
                .withSampler(new io.jaegertracing.Configuration.SamplerConfiguration().withType(SAMPLER_CONST).withParam(1));

        Tracer tracer = config.getTracer();
        log.info(tracer.toString());
        return tracer;
    }*/

/*    @Bean
    public Tracer jaegerTracer() {

        com.uber.jaeger.Configuration.SenderConfiguration sender = new com.uber.jaeger.Configuration.SenderConfiguration();
        sender.withAgentHost(hostName)
                .withAgentPort(port);

        com.uber.jaeger.Configuration config = new com.uber.jaeger.Configuration(appName);
        //withSender 选择发送方式 withLogSpans 是否日志上报 withMaxQueueSize 数据最大累计量 withFlushInterval 报告间隔的刷新( ms )
        config.withReporter(new com.uber.jaeger.Configuration.ReporterConfiguration().withSender(sender).withFlushInterval(100).withLogSpans(logSpans))
                .withSampler(new com.uber.jaeger.Configuration.SamplerConfiguration().withType(SAMPLER_CONST).withParam(1));

        io.opentracing.Tracer tracer = config.getTracer();
        log.info(tracer.toString());
        return tracer;
    }*/

/*    @Bean
    public Tracer jaegerTracer() {
        return new io.jaegertracing.Configuration(appName).getTracer();
    }*/

}
