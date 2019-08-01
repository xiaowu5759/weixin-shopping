package com.xiaowu.sell.service.impl;

import com.xiaowu.sell.converter.OrderMaster2OrderDTOConverter;
import com.xiaowu.sell.dao.OrderDetailMapper;
import com.xiaowu.sell.dao.OrderMasterMapper;
import com.xiaowu.sell.dto.CartDTO;
import com.xiaowu.sell.dto.OrderDTO;
import com.xiaowu.sell.entity.OrderDetail;
import com.xiaowu.sell.entity.OrderMaster;
import com.xiaowu.sell.entity.ProductInfo;
import com.xiaowu.sell.enums.OrderStatusEnum;
import com.xiaowu.sell.enums.PayStatusEnum;
import com.xiaowu.sell.enums.ResultEnum;
import com.xiaowu.sell.exception.SellException;
import com.xiaowu.sell.service.OrderService;
import com.xiaowu.sell.service.ProductService;
import com.xiaowu.sell.util.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author XiaoWu
 * @date 2019/8/1 14:40
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderDetailMapper orderDetailMapper;

	@Autowired
	private OrderMasterMapper orderMasterMapper;

	@Override
	public OrderDTO createOrder(OrderDTO orderDTO) {

		String orderId = KeyUtils.genUniqueKey();

		BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

		// 1. 查询商品（数量，价格）
		for (OrderDetail orderDetail : orderDTO.getOrderDetailList()){
			ProductInfo productInfo = productService.getOne(orderDetail.getProductId());
			if(productInfo == null){
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			// 2. 计算总价
			orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()))
					.add(orderAmount);
			// 订单详情入库
			BeanUtils.copyProperties(productInfo, orderDetail);
			orderDetail.setDetailId(KeyUtils.genUniqueKey());
			orderDetail.setOrderId(orderId);
			orderDetailMapper.save(orderDetail);
		}

		// 3. 写入订单数据库（orderMaster和orderDetail）
		OrderMaster orderMaster = new OrderMaster();
		// 应该先拷贝，以防覆盖，覆盖的默认值要修复
		BeanUtils.copyProperties(orderDTO, orderMaster);
		orderMaster.setOrderId(orderId);
		orderMaster.setOrderAmount(orderAmount);
		// 数据库中 设置了相应的功能了 默认值
		orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
		orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
		orderMasterMapper.save(orderMaster);

		// 4. 扣库存
		// todo 减少污染 代码模块
		List<CartDTO> cartDTOList = new ArrayList<>();
		cartDTOList = orderDTO.getOrderDetailList().stream().map(orderDetail -> new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity())).collect(Collectors.toList());
		productService.decreaseStock(cartDTOList);

		return orderDTO;
	}

	@Override
	public OrderDTO getOne(String orderId) {
		OrderMaster orderMaster = orderMasterMapper.findByOrderId(orderId);
		if (orderMaster == null
				) {
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		// 查询订单详情
		List<OrderDetail> orderDetailList = orderDetailMapper.findByOrderId(orderId);
		if(CollectionUtils.isEmpty(orderDetailList)){
			throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
		}
		OrderDTO orderDTO = new OrderDTO();
		BeanUtils.copyProperties(orderMaster, orderDTO);
		orderDTO.setOrderDetailList(orderDetailList);
		return orderDTO;
	}

	@Override
	public Page<OrderDTO> listOrder(String buyerOpenid, Pageable pageable) {
		Page<OrderMaster> orderMasterPage = orderMasterMapper.findByBuyerOpenid(buyerOpenid, pageable);
		List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
		Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
		return orderDTOPage;
	}

	@Override
	public OrderDTO cancelOrder(OrderDTO orderDTO) {
		// 1. 判断订单状态
		if(orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){

		}
		// 2. 修改订单状态

		// 3. 返回库存

		// 4. 如果已支付，需要退款

		return null;
	}

	@Override
	public OrderDTO finishOrder(OrderDTO orderDTO) {
		return null;
	}

	@Override
	public OrderDTO payOrder(OrderDTO orderDTO) {
		return null;
	}
}
