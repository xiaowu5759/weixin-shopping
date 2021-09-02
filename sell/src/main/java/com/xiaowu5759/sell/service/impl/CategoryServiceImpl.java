package com.xiaowu5759.sell.service.impl;

import com.xiaowu5759.sell.dao.ProductCategoryMapper;
import com.xiaowu5759.sell.entity.ProductCategory;
import com.xiaowu5759.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author XiaoWu
 * @date 2019/7/30 11:11
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private ProductCategoryMapper productCategoryMapper;

	@Override
	public ProductCategory getOne(Integer categoryId) {
		return productCategoryMapper.findByCategoryId(categoryId);
//		return null;
	}

	@Override
	public List<ProductCategory> listAll() {
		return productCategoryMapper.findAll();
	}

	@Override
	public List<ProductCategory> listByCategoryTypeIn(List<Integer> categoryTypeList) {
		return productCategoryMapper.findByCategoryTypeIn(categoryTypeList);
	}

	@Override
	public ProductCategory save(ProductCategory productCategory) {
		return productCategoryMapper.save(productCategory);
	}
}
