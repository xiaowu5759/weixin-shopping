package com.xiaowu.sell.dao;

import com.xiaowu.sell.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryMapperTest {
	@Autowired
	private ProductCategoryMapper productCategoryMapper;

	@Test
	public void findOneTest(){
		ProductCategory productCategory = productCategoryMapper.findByCategoryId(1);
		System.out.println(productCategory.toString());

	}

	@Test
	public void saveTest(){
		ProductCategory productCategory = new ProductCategory();
		productCategory.setCategoryName("女生最爱的");
		productCategory.setCategoryType(3);
		productCategoryMapper.save(productCategory);
	}

	@Test
	public void saveAssertTest(){
		ProductCategory productCategory = new ProductCategory("男生最爱", 5);
		ProductCategory result = productCategoryMapper.save(productCategory);
		Assert.assertNotNull(result);
		Assert.assertNotEquals(null, result);
	}

	@Test
	public void findByCategoryTypeInTest(){
		List<Integer> list = Arrays.asList(2,3,4);
		List<ProductCategory> result = productCategoryMapper.findByCategoryTypeIn(list);
		Assert.assertNotEquals(0, result.size());
	}

}