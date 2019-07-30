package com.xiaowu.sell.dao;

import com.xiaowu.sell.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author XiaoWu
 * @date 2019/7/30 14:31
 */
public interface ProductInfoMapper extends JpaRepository<ProductInfo, String> {
	List<ProductInfo> findByProductStatus(Integer productStatus);

	ProductInfo findByProductId(String productId);
}
