package com.xiaowu5759.sell.service.impl;

import com.xiaowu5759.sell.dao.SellerInfoMapper;
import com.xiaowu5759.sell.entity.SellerInfo;
import com.xiaowu5759.sell.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiaowu
 * @date 2020/6/2 15:48
 */
@Service
@Slf4j
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoMapper sellerInfoMapper;

    @Override
    public SellerInfo getSellerInfoByOpenid(String openid) {
        return sellerInfoMapper.getSellerInfoByOpenid(openid);
    }
}
