package com.example.test.controller;

import com.example.test.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Tengfei Wang
 * @escription @RefreshScope 如果配置有改动，会动态刷新当前类下的所有bean
 * @date Created in 09:28 2019-06-05
 * @modified by
 */
@RestController
@Slf4j
@Api(value = "User API")
@RefreshScope
public class ProjectController {

    @Value("${spring.application.name}")
    String name;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private UserService userService;

    @Value("${project.config.test}")
    private String test;


    @RequestMapping(value = "/tt", method = RequestMethod.GET)
    public String tt() {
        return test;
    }

    @RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
    public String hello(@PathVariable("name") String name) {
        redisTemplate.opsForSet().add("wtf", name);
        userService.getUser(name);
        return String.format("hello %s, request project is %s", name, this.name);
    }

    @RequestMapping(value = "/test",  method = RequestMethod.GET)
    public String getTest(HttpServletRequest request) {
        String header = request.getHeader("X-Request-name");
        String age = request.getHeader("X-Request-age");
        log.info("header name is {}, age is {}", header, age);
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://CONFIG-CENTER/test", String.class);
        return forEntity.getBody();
    }

    @RequestMapping(value = "/client", method = RequestMethod.GET)
    public String clientTest(HttpServletRequest request) {
        String str = "";
        try {
            HttpResponse execute = httpClient.execute(new HttpGet("http://localhost:8095/feign/hello?name=wtf"));
            HttpEntity entity = execute.getEntity();
            str = EntityUtils.toString(entity, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}
