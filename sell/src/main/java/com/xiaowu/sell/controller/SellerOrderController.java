package com.xiaowu.sell.controller;

import com.xiaowu.sell.dto.OrderDTO;
import com.xiaowu.sell.enums.ResultEnum;
import com.xiaowu.sell.exception.SellException;
import com.xiaowu.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 商户端订单
 * @author xiaowu
 * @date 2020/6/1 9:13
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单列表
     * @param page 第几页，从1页开始
     * @param size 一页有多少数据
     * @return ModelAndView
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map){
        PageRequest pageRequest = new PageRequest(page-1, size);
        Page<OrderDTO> orderDTOPage = orderService.listAllOrders(pageRequest);
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);  // 获取当前页
        map.put("size", size);
        return new ModelAndView("order/list", map);
    }

    /**
     * 卖家端取消订单
     * @param orderId 订单id
     * @return ModelAndView
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId")String orderId,
                               Map<String, Object> map){
        try {
            OrderDTO order = orderService.getOrder(orderId);
            orderService.cancelOrder(order);
            // 会将取消订单中的所有异常也同时捕获
        } catch (SellException e){
            log.error("【卖家端取消订单】出现异常{}", e);
//            e.printStackTrace();
            // ModelAndView的错误信息
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }

    /**
     * 查询订单详情
     * @param orderId 订单id
     * @return ModelAndView
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId")String orderId,
                               Map<String, Object> map){
        OrderDTO order;
        try{
            order = orderService.getOrder(orderId);
        }catch (SellException e){
            log.error("【卖家端查询订单详情】出现异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("orderDTO", order);
        return new ModelAndView("order/detail", map);
    }

    /**
     * 卖家端订单完结
     * @param orderId 订单id
     * @return ModelAndView
     */
    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId")String orderId,
                               Map<String, Object> map){
        try{
            OrderDTO order = orderService.getOrder(orderId);
            orderService.finishOrder(order);
        } catch (SellException e){
            log.error("【卖家端完结订单】出现异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }
}
