package com.xiaowu.sell.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaowu
 * @date 2020/6/2 16:56
 */
@Configuration
@ConfigurationProperties(prefix = "projecturl")
@Getter
@Setter
public class ProjectUrlProperties {

    /**
     * 微信公众平台授权url
     */
    public String wechatMpAuthorize;

    /**
     * 微信公众平台授权url
     */
    public String wechatOpenAuthorize;

    /**
     * 点餐系统url
     */
    public String sell;
}
