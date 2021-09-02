package com.xiaowu5759.sell.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author XiaoWu
 * @date 2019/8/1 13:32
 */
@Entity
@Data
public class OrderDetail {

	@Id
	private String detailId;

	// 订单id
	private String orderId;

	// 商品id
	private String productId;

	// 商品名称
	private String productName;

	// 商品价格
	private BigDecimal productPrice;

	// 商品数量
	private Integer productQuantity;

	// 商品小图
	private String productIcon;
}
