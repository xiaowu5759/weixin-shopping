package com.xiaowu.sell.enums;

import lombok.Getter;

/**
 * @author XiaoWu
 * @date 2019/8/1 13:26
 */
@Getter
public enum OrderStatusEnum {
	NEW(0,"新订单"),
	FINISHED(1,"完结订单"),
	CANCEL(2,"已取消"),
	;

	private Integer code;

	private String message;

	OrderStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
