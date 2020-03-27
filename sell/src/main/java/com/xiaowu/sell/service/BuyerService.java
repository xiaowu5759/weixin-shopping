package com.xiaowu.sell.service;

import com.xiaowu.sell.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;

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
