package com.xiaowu5759.sell.service;

import com.xiaowu5759.sell.dto.CartDTO;
import com.xiaowu5759.sell.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author XiaoWu
 * @date 2019/7/30 14:48
 */
public interface ProductService {

	ProductInfo getOne(String productId);

	/**
	 * 查询所有在架商品列表
	 * @return
	 */
	List<ProductInfo> listUpAll();

	Page<ProductInfo> listAll(Pageable pageable);

	ProductInfo save(ProductInfo productInfo);

	// 加库存
	void increaseStock(List<CartDTO> cartDTOList);

	// 减库存
	void decreaseStock(List<CartDTO> cartDTOList);

	/**
	 * 商品上架
	 * @param productId 商品id
	 * @return 商品基本信息
	 */
	ProductInfo onSale(String productId);

	/**
	 * 商品下架
	 * @param productId 商品id
	 * @return 商品基本信息
	 */
	ProductInfo offSale(String productId);
}
