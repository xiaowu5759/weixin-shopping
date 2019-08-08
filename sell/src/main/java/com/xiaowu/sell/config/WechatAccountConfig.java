package com.xiaowu.sell.config;

import lombok.Data;
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
public class WechatAccountConfig {
	private String mpAppId;

	private String mpAppSecret;
}
