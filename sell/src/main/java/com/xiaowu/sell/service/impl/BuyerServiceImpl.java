package com.xiaowu.sell.service.impl;

import com.xiaowu.sell.dto.OrderDTO;
import com.xiaowu.sell.enums.ResultEnum;
import com.xiaowu.sell.exception.SellException;
import com.xiaowu.sell.service.BuyerService;
import com.xiaowu.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author XiaoWu
 * @date 2019/8/5 12:26
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

	@Autowired
	private OrderService orderService;

	@Override
	public OrderDTO getOrderDetail(String openid, String orderId) {
		OrderDTO orderDTO = checkOrderOwner(openid, orderId);
		return orderDTO;
	}

	@Override
	public OrderDTO cancelOrder(String openid, String orderId) {
		OrderDTO orderDTO = checkOrderOwner(openid, orderId);
		if (orderDTO == null){
			log.error("【取消订单】查不到该订单,orderId={}", orderId);
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		return orderService.cancelOrder(orderDTO);
	}

	private OrderDTO checkOrderOwner(String openid, String orderId){
		OrderDTO orderDTO = orderService.getOrder(orderId);
		if (orderDTO == null){
			return null;
		}
		if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
			log.error("【查询订单】订单的openid不一致,openid=[{}],orderDTO=[{}]", openid,orderDTO);
			throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
		}
		return orderDTO;
	}
}
