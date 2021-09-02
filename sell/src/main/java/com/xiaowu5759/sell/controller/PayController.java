package com.xiaowu5759.sell.controller;

import com.lly835.bestpay.model.PayResponse;
import com.xiaowu5759.sell.dto.OrderDTO;
import com.xiaowu5759.sell.enums.ResultEnum;
import com.xiaowu5759.sell.exception.SellException;
import com.xiaowu5759.sell.service.OrderService;
import com.xiaowu5759.sell.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付
 * @author xiaowu
 * @date 2020/5/25 13:30
 */
@Slf4j
// 返回一个页面
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId")String orderId,
                               @RequestParam("returnUrl")String returnUrl){
        /** 这里思考，controller层不做太复杂的判断过程和逻辑，此处可以下沉*/
        // 1. 查询订单
        OrderDTO order = orderService.getOrder(orderId);
        if (order == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        // 2. 发起支付
        PayResponse payResponse = payService.create(order);
        Map<String, Object> map = new HashMap<>();
        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);
        return new ModelAndView("pay/create", map);
    }

    // 接受微信异步通知的
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData){

        payService.notifyData(notifyData);

        // 返回给微信处理结果
        return new ModelAndView("/pay/success");
    }
}
