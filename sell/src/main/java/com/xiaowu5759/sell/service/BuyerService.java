package com.xiaowu5759.sell.service;

import com.xiaowu5759.sell.dto.OrderDTO;

/**
 * 买家服务
 * @author XiaoWu
 * @date 2019/8/5 12:24
 */
public interface BuyerService {

	// 查询一个订单
	OrderDTO getOrderDetail(String openid,String orderId);

	// 取消订单
	OrderDTO cancelOrder(String openid, String orderId);
}
