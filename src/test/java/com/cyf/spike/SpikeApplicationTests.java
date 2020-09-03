package com.cyf.spike;

import com.cyf.spike.entity.SkUser;
import com.cyf.spike.utils.MD5Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class SpikeApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Test
    void contextLoads() {
        //stringRedisTemplate.opsForValue().set("token", "ssdhgharuo3953rhfsjlur3wofnsduf3owjfo", 60, TimeUnit.SECONDS);
        /*SkUser user = new SkUser();
        user.setId(1L);
        user.setNickname("zzz");
        user.setPassword("shdgjgdafd");
        user.setSalt("shdgsjdg");
        redisTemplate.opsForValue().set(String.valueOf(user.getId()), user, 60, TimeUnit.SECONDS);
        SkUser u = (SkUser)redisTemplate.opsForValue().get(String.valueOf(user.getId()));
        System.out.println(u);*/
       /* String token = stringRedisTemplate.opsForValue().get("token");
        System.out.println(token);*/
       System.out.println(MD5Util.formPassToDBPass("123456", "1a2b3c4d"));
    }


}
