package com.xiaowu5759.sell.controller;

import com.xiaowu5759.sell.entity.ProductCategory;
import com.xiaowu5759.sell.entity.ProductInfo;
import com.xiaowu5759.sell.exception.SellException;
import com.xiaowu5759.sell.form.ProductForm;
import com.xiaowu5759.sell.service.CategoryService;
import com.xiaowu5759.sell.service.ProductService;
import com.xiaowu5759.sell.util.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author xiaowu
 * @date 2020/6/1 16:02
 */
@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 商品列表
     * @param page 第几页，从1页开始
     * @param size 一页有多少数据
     * @return ModelAndView
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map){
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<ProductInfo> productInfos = productService.listAll(pageRequest);
        map.put("productInfoPage", productInfos);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("product/list", map);
    }

    /**
     * 商品上架
     * @param productId 商品主键
     * @return ModelAndView
     */
    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId")String productId,
                               Map<String, Object> map){
        map.put("url", "/sell/seller/product/list");
        try{
            productService.onSale(productId);
        } catch (SellException e){
            map.put("msg", e.getMessage());
            return new ModelAndView("common/error", map);
        }

        return new ModelAndView("common/success", map);
    }

    /**
     * 商品下架
     * @param productId 商品主键
     * @return ModelAndView
     */
    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId")String productId,
                               Map<String, Object> map){
        map.put("url", "/sell/seller/product/list");
        try{
            productService.offSale(productId);
        } catch (SellException e){
            map.put("msg", e.getMessage());
            return new ModelAndView("common/error", map);
        }

        return new ModelAndView("common/success", map);
    }

    /**
     * 商品详情页，商品新增和商品修改共用
     * @param productId 商品主键
     * @return ModelAndView
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false)String productId,
                              Map<String,Object> map){
        if(!StringUtils.isEmpty(productId)){
            ProductInfo product = productService.getOne(productId);
            map.put("productInfo", product);
        }
        // 查询所有的类目
        List<ProductCategory> categoryList = categoryService.listAll();
        map.put("categoryList", categoryList);

        return new ModelAndView("product/index", map);
    }

    /**
     * 修改和更新 商品
     * @param form 商品表单
     * @return ModelAndView
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }

        ProductInfo productInfo = new ProductInfo();
        try{
            // 如果productId不为空，说明是更新
            if(!StringUtils.isEmpty(form.getProductId())){
                productInfo = productService.getOne(form.getProductId());
            } else {
                form.setProductId(KeyUtils.genUniqueKey());
            }
            // 传过来新的值将旧的值覆盖
            BeanUtils.copyProperties(form, productInfo);
            productService.save(productInfo);
        }catch (SellException e){
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }
}
