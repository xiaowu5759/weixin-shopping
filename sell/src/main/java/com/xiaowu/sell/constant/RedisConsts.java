package com.xiaowu.sell.constant;

/**
 * @author xiaowu
 * @date 2020/6/3 13:15
 */
public final class RedisConsts {
    private RedisConsts(){}

    public static final String TOKEN_PREFIX = "token_%s";

    public static final Integer EXPIRE = 7200; // 2小时
}
