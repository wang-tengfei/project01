package com.example.config.aop;

import com.example.common.ProjectConstant;
import com.example.config.annotation.NexusguardJaeger;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.tag.Tags;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

/**
 * @author: Tengfei Wang
 * @description:
 * @date: Created in 08:32 2019-05-15
 * @modified by:
 */
@Component
@Slf4j
public class NexusguardJaegerConfiguration {


    @Autowired
    @Lazy
    private Tracer tracer;

    @Bean
    public TracingAspect pxTracingAspect(){
        return new TracingAspect();
    }

    @Aspect
    class TracingAspect{

        @Around("@annotation(com.example.config.annotation.NexusguardJaeger)")
        public Object pxTracingAspect(ProceedingJoinPoint joinPoint){

            Span span = null;
            try {
                if(tracer != null){
                    Class<?> aClass = joinPoint.getTarget().getClass();
                    String methodName = joinPoint.getSignature().getName();
                    Object[] args = joinPoint.getArgs();
                    Class[] cls = new Class[args.length];
                    for (int i=0; i<args.length; i++) {
                        cls[i] = args[i].getClass();
                    }
                    Method method = aClass.getMethod(methodName, cls);
                    NexusguardJaeger annotation =  method.getAnnotation(NexusguardJaeger.class);

                    String component = annotation.component();
                    if (ProjectConstant.TAG_COMPONENT_ES.equals(component)) {

                    }

                    Tracer.SpanBuilder spanBuilder = tracer.buildSpan(annotation.value())
                            .withTag(Tags.COMPONENT.getKey(), annotation.component())
                            .withTag("class", aClass.getName())
                            .withTag("method", methodName);

                    span = spanBuilder.start();
                }
                return joinPoint.proceed();
            } catch (Throwable e) {
                LinkedHashMap<String, Object> exceptionLogs = new LinkedHashMap<>(2);
                exceptionLogs.put("event", Tags.ERROR.getKey());
                exceptionLogs.put("error.object", e);
                span.log(exceptionLogs);
                Tags.ERROR.set(span, true);
                log.error(String.format("add span error: %s", e.getMessage()));
                return null;
            } finally {
                if (tracer != null && span != null) {
                    span.finish();
                }
            }
        }
    }

}
