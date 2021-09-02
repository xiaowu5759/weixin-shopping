package com.xiaowu5759.sell.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @createBy XiaoWu
 * @date 2019/8/6 15:03
 */

@Configuration
@ConfigurationProperties(prefix = "wechat")
@Getter
@Setter
public class WechatAccountProperties {
	private String mpAppId;

	private String mpAppSecret;

	/** 微信开放平台appid */
	private String openAppId;

	/** 微信开放平台appsecret */
	private String openAppSecret;

	/** 商户号 */
	private String mchId;

	/** 商户密钥 */
	private String mchKey;

	/** 商户证书路径 */
	private String keyPath;

	private String notifyUrl;
}
