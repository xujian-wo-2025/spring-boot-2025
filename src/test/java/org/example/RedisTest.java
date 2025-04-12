package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;  // 添加这行导入
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testSet(){
        // 正确的写法
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();// 添加一个实际的操作
        valueOperations.set("username", "zhangsan");
        valueOperations.set("id","1",15, TimeUnit.SECONDS);
    }

    @Test
    public void testGet(){
        //从redis中获取一个键值对
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();// 添加一个实际的操作
        System.out.println(valueOperations.get("username"));
    }
}