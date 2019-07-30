package com.xiaowu.sell.service.impl;

import com.xiaowu.sell.dao.ProductInfoMapper;
import com.xiaowu.sell.entity.ProductInfo;
import com.xiaowu.sell.enums.ProductStatusEnum;
import com.xiaowu.sell.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author XiaoWu
 * @date 2019/7/30 14:51
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

	@Autowired
	private ProductInfoMapper productInfoMapper;

	@Override
	public ProductInfo getOne(String productId) {
		return productInfoMapper.findByProductId(productId);
	}

	@Override
	public List<ProductInfo> listUpAll() {
		return productInfoMapper.findByProductStatus(ProductStatusEnum.UP.getCode());
	}

	@Override
	public Page<ProductInfo> listAll(Pageable pageable) {
		return productInfoMapper.findAll(pageable);
	}

	@Override
	public ProductInfo save(ProductInfo productInfo) {
		return productInfoMapper.save(productInfo);
	}
}
