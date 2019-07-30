package com.xiaowu.sell.dao;

import com.xiaowu.sell.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryMapper extends JpaRepository<ProductCategory, Integer> {
	List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

	ProductCategory findByCategoryId(Integer categoryId);
}
