package com.xiaowu.sell.dto;

import lombok.Data;

/**
 * 购物车
 * @author XiaoWu
 * @date 2019/8/1 15:14
 */
@Data
public class CartDTO {
	// 商品id
	private String productId;

	// 商品数量
	private Integer productQuantity;

	// todo 属性比较少的时候，i相关给构造方法
	public CartDTO(String productId, Integer productQuantity) {
		this.productId = productId;
		this.productQuantity = productQuantity;
	}
}
