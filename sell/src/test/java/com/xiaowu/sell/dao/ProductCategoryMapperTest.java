package com.xiaowu.sell.dao;

import com.xiaowu.sell.dataobject.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryMapperTest {
	@Autowired
	private ProductCategoryMapper productCategoryMapper;

	@Test
	public void findOneTest(){
		Optional<ProductCategory> productCategory = productCategoryMapper.findById(1);
		System.out.println(productCategory.toString());

	}

	@Test
	public void saveTest(){
		ProductCategory productCategory = new ProductCategory();
		productCategory.setCategoryName("女生最爱的");
		productCategory.setCategoryType(3);
		productCategoryMapper.save(productCategory);
	}

}