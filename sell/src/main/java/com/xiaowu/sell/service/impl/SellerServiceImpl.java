package com.xiaowu.sell.service.impl;

import com.xiaowu.sell.dao.SellerInfoMapper;
import com.xiaowu.sell.entity.SellerInfo;
import com.xiaowu.sell.service.SellerService;
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
