package com.xiaowu.sell.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * mp 是公众账号的意思
 * @createBy XiaoWu
 * @date 2019/8/6 14:12
 */
@Component
public class WechatMpConfig {

	@Autowired
	private WechatAccountConfig accountConfig;

	@Bean
	public WxMpService wxMpService(){
		WxMpService wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
		return wxMpService;
	}

	@Bean
	public WxMpConfigStorage wxMpConfigStorage(){
		WxMpConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
		// 配置从配置文件中读取
		((WxMpInMemoryConfigStorage) wxMpConfigStorage).setAppId(accountConfig.getMpAppId());
		((WxMpInMemoryConfigStorage) wxMpConfigStorage).setSecret(accountConfig.getMpAppId());
		return wxMpConfigStorage;
	}
}
