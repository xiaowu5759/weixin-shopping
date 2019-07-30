package com.xiaowu.sell.service;

import com.xiaowu.sell.entity.ProductCategory;

import java.util.List;

/**
 * @author XiaoWu
 * @date 2019/7/30 11:03
 */
public interface ProductCategoryService {

	ProductCategory getOne(Integer categoryId);

	List<ProductCategory> listAll();

	List<ProductCategory> listByCategoryTypeIn(List<Integer> categoryTypeList);

	ProductCategory save(ProductCategory productCategory);


}
