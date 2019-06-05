package com.example.test.service;

import com.example.common.ProjectConstant;
import com.example.config.annotation.NexusguardJaeger;
import com.example.test.dao.UserDao;
import com.example.test.domain.User;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * @author: Tengfei Wang
 * @description:
 * @date: Created in 14:04 2019-05-14
 * @modified by:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JestClient jestClient;

    @Autowired
    private RestClient restClient;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private Tracer tracer;

    @Override
    public User saveUser(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setCreatTime(new Date());
        return userDao.save(user);
    }

    @Override
    public User getUser(String id) {
        Span start = tracer.buildSpan("test-11111").start();
        start.setTag("test", "11111");
        start.setTag("error", "true");
        start.setBaggageItem("baggage", "this is a baggage");
        start.finish();
        User user = userDao.findFirstById("8876e5c8-b2e7-43c2-9931-be88e4ea0434");
        redisTemplate.opsForSet().add(user.getId(), user.toString());
        return userDao.findFirstById(id);
    }

    @Override
    public Object getUserByRest(String id) {
        try {
            Request request = new Request(HttpMethod.GET.name(), "jaeger-service-2019-05-14");
            Response response = restClient.performRequest(request);
            return EntityUtils.toString(response.getEntity());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object getEsInfo() {
        String jsonObject = null;
        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex("jaeger-service-2019-05-14").addType("service").build();
            SearchResult execute = jestClient.execute(search);
            jsonObject = execute.getJsonString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
