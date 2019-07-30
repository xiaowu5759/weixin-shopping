package com.xiaowu.sell.service;

import com.xiaowu.sell.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author XiaoWu
 * @date 2019/7/30 14:48
 */
public interface ProductInfoService {

	ProductInfo getOne(String productId);

	/**
	 * 查询所有在架商品列表
	 * @return
	 */
	List<ProductInfo> listUpAll();

	Page<ProductInfo> listAll(Pageable pageable);

	ProductInfo save(ProductInfo productInfo);

	// 加库存

	// 减库存
}
