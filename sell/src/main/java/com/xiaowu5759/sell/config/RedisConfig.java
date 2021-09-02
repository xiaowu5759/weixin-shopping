package com.xiaowu5759.sell.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author xiaowu
 * @date 2020/6/3 10:49
 */
@Configuration
public class RedisConfig {

    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        RedisSerializer<Object> jsonString = new FastJsonRedisSerializer<>(Object.class);
        // String 的 key 和 hash 的 key 都采用 String 的序列化方式
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);

        // value 都采用 fastjson 的序列化方式
        // 此序列化方式，字符串会带上双引号，String.valueOf(abc)也可以还原回来
        redisTemplate.setValueSerializer(jsonString);
        redisTemplate.setHashValueSerializer(jsonString);
        return redisTemplate;
    }
}
