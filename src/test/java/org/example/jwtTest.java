package org.example;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class jwtTest {

    @Test
    public void testGen() throws Exception {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "张三");

        // 方案1：将 Map 转为 JSON 字符串
        String userJson = new ObjectMapper().writeValueAsString(claims);

        String token = JWT.create()
                .withClaim("user", userJson)  // 存储为 JSON
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                .sign(Algorithm.HMAC256("itheima"));

        System.out.println(token);
    }
}