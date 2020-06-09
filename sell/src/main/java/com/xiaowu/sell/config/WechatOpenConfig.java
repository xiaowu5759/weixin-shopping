package com.xiaowu.sell.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaowu
 * @date 2020/6/2 16:17
 */
@Configuration
public class WechatOpenConfig {
    /* 由于授权流程都和公众号一样，所以利用的sdk和配置相同 */
    /* 使用的接口名字不同，需要处理一下 */

    @Autowired
    private WechatAccountProperties wechatAccountProperties;

    @Bean
    public WxMpService wxOpenService(WxMpConfigStorage wxOpenConfigStorage){
        WxMpService wxOpenService = new WxMpServiceImpl();
        wxOpenService.setWxMpConfigStorage(wxOpenConfigStorage);
        return wxOpenService;
    }

    @Bean
    public WxMpConfigStorage wxOpenConfigStorage(){
        WxMpConfigStorage wxOpenConfigStorage = new WxMpInMemoryConfigStorage();
        // 配置从配置文件中读取
        ((WxMpInMemoryConfigStorage) wxOpenConfigStorage).setAppId(wechatAccountProperties.getOpenAppId());
        ((WxMpInMemoryConfigStorage) wxOpenConfigStorage).setSecret(wechatAccountProperties.getOpenAppSecret());
        return wxOpenConfigStorage;
    }

}
