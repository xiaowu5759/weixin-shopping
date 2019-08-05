package com.xiaowu.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author XiaoWu
 * @date 2019/8/5 14:31
 */
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {

	@GetMapping("/auth")
	public void auth(@RequestParam("code") String code){
		log.info("code=[{}]", code);
		// 拿着code,换取access_token

	}
}
