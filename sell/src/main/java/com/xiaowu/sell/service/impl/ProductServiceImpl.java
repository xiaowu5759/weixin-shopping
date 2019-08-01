package com.xiaowu.sell.service.impl;

import com.xiaowu.sell.dao.ProductInfoMapper;
import com.xiaowu.sell.dto.CartDTO;
import com.xiaowu.sell.entity.ProductInfo;
import com.xiaowu.sell.enums.ProductStatusEnum;
import com.xiaowu.sell.enums.ResultEnum;
import com.xiaowu.sell.exception.SellException;
import com.xiaowu.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author XiaoWu
 * @date 2019/7/30 14:51
 */
@Service
public class ProductServiceImpl implements ProductService {

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

	@Override
	public void increaseStock(List<CartDTO> cartDTOList) {

	}

	@Override
	@Transactional
	public void decreaseStock(List<CartDTO> cartDTOList) {
		for(CartDTO cartDTO:cartDTOList){
			ProductInfo productInfo = productInfoMapper.getOne(cartDTO.getProductId());
			if(productInfo == null){
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
			if (result < 0) {
				throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
			}
			productInfo.setProductStock(result);
			productInfoMapper.save(productInfo);
		}
	}
}
