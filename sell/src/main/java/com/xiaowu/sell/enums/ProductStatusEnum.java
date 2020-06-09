package com.xiaowu.sell.enums;

import lombok.Getter;

/**
 * 商品状态
 * @author XiaoWu
 * @date 2019/7/30 14:52
 */
@Getter
public enum ProductStatusEnum implements CodeEnum{

	UP(0, "在架"),
	DOWN(1, "下架")
	;

	private Integer code;

	private String message;

	ProductStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
