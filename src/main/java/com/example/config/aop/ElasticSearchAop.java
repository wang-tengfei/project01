package com.example.config.aop;

import com.example.common.ProjectConstant;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.searchbox.client.config.ElasticsearchVersion;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author: Tengfei Wang
 * @description: support elastic search (jestClient)
 * @date: Created in 17:48 2019-05-14
 * @modified by:
 */
@Aspect
@Component
@Slf4j
public class ElasticSearchAop {

    @Autowired
    private Tracer tracer;

    @Pointcut("execution(* io.searchbox.client.http.JestHttpClient.execute(..))")
    public void pointcut() {
    }


    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        Search search;
        Span span = null;
        try {
            Optional<Object> first = Arrays.stream(args).filter(w -> w instanceof Search).findFirst();
            if (first.isPresent()) {
                search = (Search) first.get();
                span = tracer.buildSpan(search.getRestMethodName()).start();
                span.setTag("http.url", search.getURI(ElasticsearchVersion.V55));
                span.setTag("http.method", search.getRestMethodName());
                span.setTag("span.kind", "client");
                span.setTag("component", ProjectConstant.TAG_COMPONENT_ES);
            }

            if (span == null) {
                return joinPoint.proceed();
            }
            SearchResult proceed = (SearchResult) joinPoint.proceed();
            span.setTag("http.status_code", proceed.getResponseCode());
            return proceed;
        } catch (Throwable throwable) {
            log.error("add tracer exception {}", throwable.getMessage());
            if (span != null) {
                span.setTag("error", true);
                span.setTag("exception", throwable.getMessage());
            }
            return joinPoint.proceed();
        } finally {
            if (span != null) {
                span.finish();
            }
        }

    }
}
