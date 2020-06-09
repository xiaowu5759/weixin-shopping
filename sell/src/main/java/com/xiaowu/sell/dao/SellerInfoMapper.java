package com.xiaowu.sell.dao;

import com.xiaowu.sell.entity.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xiaowu
 * @date 2020/6/2 15:35
 */
public interface SellerInfoMapper extends JpaRepository<SellerInfo, String> {

    /* 自身带有新增等基础方法 */

    /**
     * 通过openid查询
     * @param openid 微信openid
     * @return SellerInfo
     */
    SellerInfo getSellerInfoByOpenid(String openid);

}
