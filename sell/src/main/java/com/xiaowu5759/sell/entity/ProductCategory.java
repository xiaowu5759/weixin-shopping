package com.xiaowu5759.sell.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author XiaoWu
 * @date 2019/7/29 16:58
 */
@Data
@Entity
// 不影响数据库的默认值
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

	/** 创建时间 */
	private Date createTime;

	/** 修改时间 */
	private Date updateTime;

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
