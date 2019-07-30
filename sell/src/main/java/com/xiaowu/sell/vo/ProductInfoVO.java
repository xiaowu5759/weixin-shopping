package com.xiaowu.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品详情
 * @author XiaoWu
 * @date 2019/7/30 15:34
 */
@Data
public class ProductInfoVO {

	@JsonProperty("id")
	private String productId;

	@JsonProperty("name")
	private String productName;

	@JsonProperty("price")
	private BigDecimal productPirce;

	@JsonProperty("description")
	private String productDescription;

	@JsonProperty("icon")
	private String productIcon;

}
