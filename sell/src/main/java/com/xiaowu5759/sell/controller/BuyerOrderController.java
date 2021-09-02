package com.xiaowu5759.sell.controller;

import com.xiaowu5759.sell.converter.OrderForm2OrderDTOConverter;
import com.xiaowu5759.sell.dto.OrderDTO;
import com.xiaowu5759.sell.enums.ResultEnum;
import com.xiaowu5759.sell.exception.SellException;
import com.xiaowu5759.sell.form.OrderForm;
import com.xiaowu5759.sell.service.BuyerService;
import com.xiaowu5759.sell.service.OrderService;
import com.xiaowu5759.sell.util.ResultVOUtils;
import com.xiaowu5759.sell.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author XiaoWu
 * @date 2019/8/2 15:57
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    // 这是校验表单的和json数据是否一致呢
    @PostMapping("/create")
    //创建订单
    public ResultVO<Map<String, String>> postBuyerOrder(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确,orderForm=[{}]", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        // todo 各层之间，设计到很多的对象转换
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createOrderResult = orderService.createOrder(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", createOrderResult.getOrderId());
        return ResultVOUtils.success(map);
    }

    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> getBuyerOrderList(@RequestParam("openid") String openid, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest request = new PageRequest(page, size);
        Page<OrderDTO> orderDTOPage = orderService.listOrder(openid, request);

        // 转存Date -> Long
        return ResultVOUtils.success(orderDTOPage.getContent());
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> getBuyerOrderDetail(@RequestParam("openid") String openid, @RequestParam("orderId") String orderId) {
        // todo 不安全的做法，改进
        OrderDTO orderDTO = buyerService.getOrderDetail(openid, orderId);
        // 会抛出异常，逻辑上不是很好
//		if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
//
//		}
        return ResultVOUtils.success(orderDTO);
    }

    //取消订单
    @PostMapping("/cancel")
    public ResultVO<OrderDTO> postCancelBuyerOrder(@RequestParam("openid") String openid, @RequestParam("orderId") String orderId) {

        // todo 不安全的做法，改进
//		OrderDTO orderDTO = orderService.getOrder(orderId);
//		orderService.cancelOrder(orderDTO);
        buyerService.cancelOrder(openid, orderId);

        return ResultVOUtils.success();
    }

}
