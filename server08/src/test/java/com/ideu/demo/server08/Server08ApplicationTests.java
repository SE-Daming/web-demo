package com.ideu.demo.server08;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class Server08ApplicationTests {

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Test
    void contextLoads() {
        System.out.println(stringRedisTemplate);
    }

}
