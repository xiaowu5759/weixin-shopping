package com.xiaowu.sell.config;

import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaowu
 * @date 2020/5/25 14:02
 */
@Configuration
public class WechatPayConfig {

    @Autowired
    private WechatAccountProperties wechatAccountProperties;

    @Bean
    public BestPayService bestPayService(WxPayConfig wxPayConfig){
        BestPayService bestPayService = new BestPayServiceImpl();
        ((BestPayServiceImpl) bestPayService).setWxPayConfig(wxPayConfig);
        return bestPayService;
    }

    @Bean
    public WxPayConfig wxPayConfig(){
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId(wechatAccountProperties.getMpAppId());
        wxPayConfig.setAppSecret(wechatAccountProperties.getMpAppSecret());
        wxPayConfig.setMchId(wechatAccountProperties.getMchId());
        wxPayConfig.setMchKey(wechatAccountProperties.getMchKey());
        wxPayConfig.setKeyPath(wechatAccountProperties.getKeyPath());
        wxPayConfig.setNotifyUrl(wechatAccountProperties.getNotifyUrl());
        return wxPayConfig;
    }

}
