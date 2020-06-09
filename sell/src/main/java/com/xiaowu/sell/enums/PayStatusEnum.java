package com.xiaowu.sell.enums;

import lombok.Getter;

/**
 * @author XiaoWu
 * @date 2019/8/1 13:29
 */
@Getter
public enum PayStatusEnum implements CodeEnum{
	WAIT(0,"等待支付"),
	SUCCESS(1, "支付成功"),
	;

	private Integer code;

	private String message;

	PayStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
