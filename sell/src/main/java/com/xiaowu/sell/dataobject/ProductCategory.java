package com.xiaowu.sell.dataobject;

import lombok.Data;

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
public class ProductCategory {

	// 类目id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer categoryId;

	// 类目名字
	private String categoryName;

	// 类目编号
	private Integer categoryType;

}
