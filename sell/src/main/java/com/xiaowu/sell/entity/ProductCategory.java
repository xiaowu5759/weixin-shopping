package com.xiaowu.sell.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author XiaoWu
 * @date 2019/7/29 16:58
 */
@Data
@Entity
@DynamicUpdate
public class ProductCategory {

	// 类目id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer categoryId;

	// 类目名字
	private String categoryName;

	// 类目编号
	private Integer categoryType;

	public ProductCategory() {
	}

	public ProductCategory(String categoryName, Integer categoryType) {
		this.categoryName = categoryName;
		this.categoryType = categoryType;
	}

	//	private Date createTime;
//
//	private Date updateTime;

}
