package com.example.config.annotation;

import java.lang.annotation.*;

/**
 * @author: Tengfei Wang
 * @description:
 * @date: Created in 08:24 2019-05-15
 * @modified by:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface NexusguardJaeger {

    String value() default "Tracer";

    String component();
}
