package com.xiaowu5759.sell.enums;

import lombok.Getter;

/**
 * @author XiaoWu
 * @date 2019/8/1 13:26
 */
@Getter
public enum OrderStatusEnum implements CodeEnum{
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

	// 通过code 遍历返回
//	public static OrderStatusEnum getOrderStatusEnum(Integer code){
//		for (OrderStatusEnum orderStatusEnum: OrderStatusEnum.values()){
//			if(orderStatusEnum.getCode().equals(code)){
//				return orderStatusEnum;
//			}
//		}
//		return null;
//	}
}
