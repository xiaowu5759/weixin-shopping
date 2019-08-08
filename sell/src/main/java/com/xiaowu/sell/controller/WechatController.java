package com.xiaowu.sell.controller;

import com.xiaowu.sell.config.WechatMpConfig;
import com.xiaowu.sell.enums.ResultEnum;
import com.xiaowu.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;

/**
 * @createBy XiaoWu
 * @date 2019/8/6 14:06
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

	@Autowired
	private WxMpService wxMpService;

	@GetMapping("/authorize")
	public String getWechatAuthorize(@RequestParam("returnUrl") String returnUrl){
		// 1. 配置
		// 2. 调用方法
		String url = "http://www.pinzhi365.com/sell/wechat/userInfo";
		// WxConsts.OAuth2Scope.SNSAPI_BASE
		// snsapi_login
		String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url,WxConsts.OAuth2Scope.SNSAPI_BASE , URLEncoder.encode(returnUrl));
		log.info("【微信网页授权】获取code,result=[{}]", redirectUrl);
		return "redirect:" + redirectUrl;
	}

	@GetMapping("/userInfo")
	public String getWechatUserInfo(@RequestParam("code") String code, @RequestParam("state") String returnUrl){
		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
		try {
			wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
		} catch (WxErrorException e) {
			log.error("【微信网页授权】{}", e);
			throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
		}
		String openId = wxMpOAuth2AccessToken.getOpenId();

		return "redirect:" + returnUrl + "?openid=" + openId;
	}
}
