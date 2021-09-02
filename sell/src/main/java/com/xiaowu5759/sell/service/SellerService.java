package com.xiaowu5759.sell.service;

import com.xiaowu5759.sell.entity.SellerInfo;

/**
 * @author xiaowu
 * @date 2020/6/2 15:45
 */
public interface SellerService {

    /**
     * 查询卖家登录信息
     * @param openid 微信openid
     * @return SellerInfo
     */
    SellerInfo getSellerInfoByOpenid(String openid);
}
