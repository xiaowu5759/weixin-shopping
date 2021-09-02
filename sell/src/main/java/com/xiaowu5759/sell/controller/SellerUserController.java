package com.xiaowu5759.sell.controller;

import com.xiaowu5759.sell.config.ProjectUrlProperties;
import com.xiaowu5759.sell.constant.CookieConsts;
import com.xiaowu5759.sell.constant.RedisConsts;
import com.xiaowu5759.sell.entity.SellerInfo;
import com.xiaowu5759.sell.enums.ResultEnum;
import com.xiaowu5759.sell.service.SellerService;
import com.xiaowu5759.sell.util.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 卖家用户相关的操作
 * @author xiaowu
 * @date 2020/6/2 17:06
 */
@Controller
@RequestMapping("/seller")
@Slf4j
public class SellerUserController {

    @Autowired
    private ProjectUrlProperties projectUrlProperties;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 卖家端登录逻辑
     * @param openid 微信openid
     * @return ModelAndView
     */
    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid")String openid,
                              HttpServletResponse response,
                              Map<String, Object> map){
        // openid去和数据库里的数据匹配
        SellerInfo sellerInfo = sellerService.getSellerInfoByOpenid(openid);
        if (sellerInfo == null){
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error");
        }

        // 设置token至redis
        String token = UUID.randomUUID().toString();
        int expire = RedisConsts.EXPIRE;
        redisTemplate.opsForValue().set(String.format(RedisConsts.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);

        // 设置token至cookie
        CookieUtils.set(response,CookieConsts.TOKEN, token, expire);

        // 跳转的时候，建议使用绝对路径
        return new ModelAndView("redirect:" + projectUrlProperties.getSell() + "/sell/seller/order/list");
    }

    /**
     * 卖家端登出操作
     * @param request
     * @param response
     * @return
     */
    @GetMapping("logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String, Object> map){
        // 从cookie里查询
        Cookie cookie = CookieUtils.get(request, CookieConsts.TOKEN);
        if(cookie != null){
            // 清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConsts.TOKEN_PREFIX, cookie.getName()));
            // 清除cookie
            CookieUtils.set(response, CookieConsts.TOKEN, null, 0);
        }

        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }
}
