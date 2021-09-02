package com.xiaowu5759.sell.service.impl;

import com.xiaowu5759.sell.dao.ProductInfoMapper;
import com.xiaowu5759.sell.dto.CartDTO;
import com.xiaowu5759.sell.entity.ProductInfo;
import com.xiaowu5759.sell.enums.ProductStatusEnum;
import com.xiaowu5759.sell.enums.ResultEnum;
import com.xiaowu5759.sell.exception.SellException;
import com.xiaowu5759.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		ProductInfo product = productInfoMapper.findByProductId(productId);
		if(product == null){
			throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
		}
		return product;
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
	@Transactional
	public void increaseStock(List<CartDTO> cartDTOList) {
		for (CartDTO cartDTO : cartDTOList){
			ProductInfo productInfo = productInfoMapper.findByProductId(cartDTO.getProductId());
			if(productInfo == null){
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
			productInfo.setProductStock(result);
			productInfoMapper.save(productInfo);
		}
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

	@Override
	public ProductInfo onSale(String productId) {
		ProductInfo product = productInfoMapper.findByProductId(productId);
		if(product == null){
			throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
		}
		// 判断商品状态
		if(ProductStatusEnum.UP.getCode().equals(product.getProductStatus())){
			throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
		}
		product.setProductStatus(ProductStatusEnum.UP.getCode());
		// 更新
		return productInfoMapper.save(product);
	}

	@Override
	public ProductInfo offSale(String productId) {
		ProductInfo product = productInfoMapper.findByProductId(productId);
		if(product == null){
			throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
		}
		// 判断商品状态
		if(ProductStatusEnum.DOWN.getCode().equals(product.getProductStatus())){
			throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
		}
		product.setProductStatus(ProductStatusEnum.DOWN.getCode());
		// 更新
		return productInfoMapper.save(product);
	}
}
