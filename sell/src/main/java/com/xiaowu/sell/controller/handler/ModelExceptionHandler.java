package com.xiaowu.sell.controller.handler;

import com.xiaowu.sell.config.ProjectUrlProperties;
import com.xiaowu.sell.exception.SellerAuthorizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author xiaowu
 * @date 2020/6/3 15:02
 */
@ControllerAdvice
public class ModelExceptionHandler {

    @Autowired
    private ProjectUrlProperties projectUrlProperties;

    //拦截登录异常
    // 跳转到二维码扫描登录页面，拼接地址
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView sellerAuthorizeException(SellerAuthorizeException e){
        return new ModelAndView("redirect:"
        .concat(projectUrlProperties.getWechatOpenAuthorize())
        .concat("/sell/wechat/qrAuthorize")
        .concat("?returnUrl=")
        .concat(projectUrlProperties.getSell())
        .concat("/sell/seller/login"));
    }
}
