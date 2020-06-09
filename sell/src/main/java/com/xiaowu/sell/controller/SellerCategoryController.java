package com.xiaowu.sell.controller;

import com.xiaowu.sell.entity.ProductCategory;
import com.xiaowu.sell.exception.SellException;
import com.xiaowu.sell.form.CategoryForm;
import com.xiaowu.sell.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 卖家类目
 *
 * @author xiaowu
 * @date 2020/6/2 13:31
 */
@Controller
@RequestMapping("seller/category")
@Slf4j
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取类目列表
     * @return ModelAndView
     */
    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map) {
        List<ProductCategory> categoryList = categoryService.listAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("category/list", map);
    }

    /**
     * 修改和新增共用，展示详情
     * @param categoryId 类目主键
     * @return ModelAndView
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                              Map<String, Object> map) {
        // categoryId 是一个自增id
        if(categoryId != null){
            ProductCategory productCategory = categoryService.getOne(categoryId);
            map.put("productCategory", productCategory);
        }

        return new ModelAndView("category/index", map);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map){
        map.put("url", "/sell/seller/category/list");
        if(bindingResult.hasErrors()){
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            return new ModelAndView("common/error", map);
        }

        ProductCategory productCategory = new ProductCategory();
        try{
            // 区别是修改还是新增
            if(form.getCategoryId() != null){
                productCategory = categoryService.getOne(form.getCategoryId());
            }
            BeanUtils.copyProperties(form, productCategory);
            categoryService.save(productCategory);
        }catch (SellException e){
            map.put("msg", e.getMessage());
            return new ModelAndView("common/error", map);
        }

        return new ModelAndView("common/success", map);
    }
}
