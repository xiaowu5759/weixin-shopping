package com.xiaowu.sell.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author xiaowu
 * @date 2020/6/2 15:24
 */
@Data
@Entity
public class SellerInfo {

    /** 用户主键 */
    @Id
    private String sellerId;

    /** 用户名 */
    private String username;

    /** 用户密码 */
    private String password;

    /** 微信openid */
    private String openid;

}
