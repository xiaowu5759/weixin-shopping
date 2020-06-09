package com.xiaowu.sell.controller;

import com.xiaowu.sell.entity.ProductCategory;
import com.xiaowu.sell.entity.ProductInfo;
import com.xiaowu.sell.service.CategoryService;
import com.xiaowu.sell.service.ProductService;
import com.xiaowu.sell.util.ResultVOUtils;
import com.xiaowu.sell.vo.ProductInfoVO;
import com.xiaowu.sell.vo.ProductVO;
import com.xiaowu.sell.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品
 * @author XiaoWu
 * @date 2019/7/30 15:19
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService productCategoryService;

	@GetMapping("/list")
	public ResultVO getBuyerProductList(){
		// 1. 查询所有上架商品
		List<ProductInfo> productInfos = productService.listUpAll();

		// 2. 查询类目（一次性查询）
//		List<Integer> cateTypeList = new ArrayList<>();
		// lambda表达式
		List<Integer> cateTypeList = productInfos.stream().map(ProductInfo::getCategoryType).collect(Collectors.toList());
		List<ProductCategory> productCategories = productCategoryService.listByCategoryTypeIn(cateTypeList);

		// 3. 数据拼装
		List<ProductVO> productVOList = new ArrayList<>();
		for (ProductCategory productCategory : productCategories){
			ProductVO productVO = new ProductVO();
			productVO.setCategoryType(productCategory.getCategoryType());
			productVO.setCategoryName(productCategory.getCategoryName());

			List<ProductInfoVO> productInfoVOList = new ArrayList<>();
			for(ProductInfo productInfo : productInfos){
				if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
					ProductInfoVO productInfoVO = new ProductInfoVO();
					BeanUtils.copyProperties(productInfo, productInfoVO);
					productInfoVOList.add(productInfoVO);
				}
			}
			productVO.setProductInfoVOList(productInfoVOList);
			productVOList.add(productVO);
		}

		return ResultVOUtils.success(productVOList);
	}
}
