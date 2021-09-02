package com.xiaowu5759.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品(包含类目)
 * @author XiaoWu
 * @date 2019/7/30 15:30
 */
@Data
public class ProductVO {

	@JsonProperty("name")
	private String categoryName;

	@JsonProperty("type")
	private Integer categoryType;

	@JsonProperty("foods")
	private List<ProductInfoVO> productInfoVOList;

}
