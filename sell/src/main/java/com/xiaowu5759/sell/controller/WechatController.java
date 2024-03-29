package com.xiaowu5759.sell.controller;

import com.xiaowu5759.sell.config.ProjectUrlProperties;
import com.xiaowu5759.sell.enums.ResultEnum;
import com.xiaowu5759.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    private ProjectUrlProperties projectUrlProperties;

	@Autowired
	@Qualifier("wxMpService")
	private WxMpService wxMpService;

	@Autowired
	@Qualifier("wxOpenService")
	private WxMpService wxOpenService;

	@GetMapping("/authorize")
	public String getWechatAuthorize(@RequestParam("returnUrl") String returnUrl){
		// 1. 配置
		// 2. 调用方法

		// 这也是写在配置文件里面的，微信用户信息的回调地址
		String url = projectUrlProperties.getWechatMpAuthorize() + "/sell/wechat/userInfo";
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
			// 将错误向上抛
			throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
		}
		String openId = wxMpOAuth2AccessToken.getOpenId();

		return "redirect:" + returnUrl + "?openid=" + openId;
	}

	@GetMapping("/qrAuthorize")
    public String qrAuthorize(@RequestParam("returnUrl")String returnUrl){
	    String url = projectUrlProperties.getWechatOpenAuthorize() +  "/sell/wechat/qrUserInfo";

	    String redirectUrl = wxOpenService.buildQrConnectUrl(url, WxConsts.QrConnectScope.SNSAPI_LOGIN, URLEncoder.encode(returnUrl));
        return "redirect:" + redirectUrl;
	}

	@GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("code")String code,
                             @RequestParam("state")String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}", e);
            // 将错误向上抛
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        log.info("wxMpOAuth2AccessToken={}",wxMpOAuth2AccessToken);
        String openId = wxMpOAuth2AccessToken.getOpenId();

        return "redirect:" + returnUrl + "?openid=" + openId;
    }

}
