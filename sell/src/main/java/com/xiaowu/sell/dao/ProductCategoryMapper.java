package com.xiaowu.sell.dao;

import com.xiaowu.sell.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryMapper extends JpaRepository<ProductCategory, Integer> {
}
