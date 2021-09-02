package com.xiaowu5759.sell.service;

import com.xiaowu5759.sell.entity.ProductCategory;

import java.util.List;

/**
 * @author XiaoWu
 * @date 2019/7/30 11:03
 */
public interface CategoryService {

	ProductCategory getOne(Integer categoryId);

	List<ProductCategory> listAll();

	List<ProductCategory> listByCategoryTypeIn(List<Integer> categoryTypeList);

	ProductCategory save(ProductCategory productCategory);


}
