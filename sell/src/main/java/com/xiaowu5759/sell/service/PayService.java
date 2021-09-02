package com.xiaowu5759.sell.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.xiaowu5759.sell.dto.OrderDTO;

/**
 * @author xiaowu
 * @date 2020/5/25 13:46
 */
public interface PayService {

    PayResponse create(OrderDTO orderDTO);

    PayResponse notifyData(String notifyData);

    // 微信退款
    RefundResponse refund(OrderDTO orderDTO);
}
