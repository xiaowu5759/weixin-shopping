package com.xiaowu5759.sell.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * cookie工具类
 * @author xiaowu
 * @date 2020/6/3 13:26
 */
public class CookieUtils {

    /**
     * 设置cookie
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void set(HttpServletResponse response,String name, String value, int maxAge){
         Cookie cookie = new Cookie(name, value);
         cookie.setPath("/");
         cookie.setMaxAge(maxAge);
         response.addCookie(cookie);
    }

    public static Cookie get(HttpServletRequest request,
                           String name){
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if(cookieMap.containsKey(name)){
            return cookieMap.get(name);
        } else {
            return null;
        }
    }

    private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie: cookies){
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}
