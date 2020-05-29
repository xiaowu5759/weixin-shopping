package com.xiaowu.sell.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.xiaowu.sell.dto.OrderDTO;
import com.xiaowu.sell.enums.ResultEnum;
import com.xiaowu.sell.exception.SellException;
import com.xiaowu.sell.service.OrderService;
import com.xiaowu.sell.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author xiaowu
 * @date 2020/5/25 13:46
 */
@Slf4j
@Service
public class PayServiceImpl implements PayService {

    // 下单名称
    private static final String ORDER_NAME = "续费测试";

    @Autowired
    private BestPayService bestPayService;

    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        /**这个sdk没有依赖spring,具有良好的可移植性
         * 但是我们将它注册成 bean
         * */
        PayRequest payRequest = new PayRequest();
        /** orderId */
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        /** 订单金额 */
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        /** 订单业务号 */
        payRequest.setOrderId(orderDTO.getOrderId());
        /** 订单名字 */
        payRequest.setOrderName(ORDER_NAME);
        /** 支付类型*/
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_MP);

        log.info("best微信支付，【payRequest={}】",payRequest);
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("best微信支付，【payResponse={}】",payResponse);
        return payResponse;
    }

    // 关键业务功能点，需要更多的日志支持
    @Override
    public PayResponse notifyData(String notifyData) {
        // 支付异步通知的关注安全点
//        1. 验证签名
//        2. 异步通知不一定是成功支付的，支付的状态
//        3. 支付金额的对比
//        4. 支付人（下单人 == 支付人）业务是否允许是同一个人

        // sdk中处理异步通知
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】异步通知，结果数据，payResponse={}",payResponse);

        // 修改订单支付状态
        OrderDTO order = orderService.getOrder(payResponse.getOrderId());
        if(order == null){
            log.error("【微信支付】异步通知，订单不存在，orderId={}",payResponse.getOrderId());
            throw(new SellException(ResultEnum.ORDER_NOT_EXIST));
        }
        // 判断金额是否一致
        // (0.10 0.1)
        if(order.getOrderAmount().compareTo(BigDecimal.valueOf(payResponse.getOrderAmount())) != 0){
            log.error("【微信支付】异步通知，订单金额不一致，orderId={}，微信通知金额={}，系统金额={}",
                    payResponse.getOrderId(),
                    payResponse.getOrderAmount(),
                    order.getOrderAmount());
            throw(new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR));
        }

        orderService.payOrder(order);
        return payResponse;
    }

    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_MP);
        log.info("【微信退款】refundRequest={}",refundRequest);
        RefundResponse response = bestPayService.refund(refundRequest);
        log.info("【微信退款】response={}",response);

        return response;
    }
}
