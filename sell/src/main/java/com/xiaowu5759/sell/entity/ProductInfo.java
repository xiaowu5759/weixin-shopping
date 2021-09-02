package com.xiaowu5759.sell.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xiaowu5759.sell.enums.ProductStatusEnum;
import com.xiaowu5759.sell.util.EnumUtils;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author XiaoWu
 * @date 2019/7/30 14:22
 */
@Entity
@Data
@DynamicUpdate
public class ProductInfo {

	@Id
	private String productId;

	// 名字
	private String productName;

	// 单价
	private BigDecimal productPrice;

	// 库存
	private Integer productStock;

	// 描述
	private String productDescription;

	// 小图
	private String productIcon;

	// 状态，0正常1下架
	private Integer productStatus = ProductStatusEnum.UP.getCode();

	// 类目编号
	private Integer categoryType;

	/** 创建时间 */
	private Date createTime;

	/** 更新时间 */
	private Date updateTime;

	@JsonIgnore
	public ProductStatusEnum getProductStatusEnum(){
		return EnumUtils.getByCode(productStatus, ProductStatusEnum.class);
	}
}
