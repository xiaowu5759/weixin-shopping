package com.xiaowu5759.sell.aspect;

import com.xiaowu5759.sell.constant.CookieConsts;
import com.xiaowu5759.sell.constant.RedisConsts;
import com.xiaowu5759.sell.exception.SellerAuthorizeException;
import com.xiaowu5759.sell.util.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author xiaowu
 * @date 2020/6/3 14:43
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 定义切点
    // 定义在方法上面，括号里记得有两个..
    @Pointcut("execution(public * com.xiaowu5759.sell.controller.Seller*.*(..))" +
            "&& !execution(public * com.xiaowu5759.sell.controller.SellerUserController.*(..))" +
    "&& !execution(public * com.xiaowu5759.sell.controller.SellerOrderController.list(..))")
    public void verify(){}

    // 切点操作
    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 查询cookie
        Cookie cookie = CookieUtils.get(request, CookieConsts.TOKEN);
        if (cookie == null){
            log.warn("【登录校验】Cookie中查不到token");
            throw new SellerAuthorizeException();
        }

        // 存在token，去redis里查询
        Object tokenValue = redisTemplate.opsForValue().get(String.format(RedisConsts.TOKEN_PREFIX, cookie.getValue()));
//        String.valueOf(tokenValue)
        if (tokenValue == null) {
            log.warn("【登录校验】Redis中查不到token");
            throw new SellerAuthorizeException();
        }
    }
}
