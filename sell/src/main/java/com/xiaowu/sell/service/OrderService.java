package com.xiaowu.sell.service;

import com.xiaowu.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author XiaoWu
 * @date 2019/8/1 14:26
 */
public interface OrderService {
	// 创建订单
	OrderDTO createOrder(OrderDTO orderDTO);

	// 查询单个订单
	OrderDTO getOne(String orderId);

	// 查询订单列表
	Page<OrderDTO> listOrder(String buyerOpenid, Pageable pageable);

	// 取消订单
	OrderDTO cancelOrder(OrderDTO orderDTO);

	// 完结订单
	OrderDTO finishOrder(OrderDTO orderDTO);

	// 支付订单
	OrderDTO payOrder(OrderDTO orderDTO);
}
