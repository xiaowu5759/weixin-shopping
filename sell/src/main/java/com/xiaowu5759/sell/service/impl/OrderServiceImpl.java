package com.xiaowu5759.sell.service.impl;

import com.xiaowu5759.sell.converter.OrderMaster2OrderDTOConverter;
import com.xiaowu5759.sell.dao.OrderDetailMapper;
import com.xiaowu5759.sell.dao.OrderMasterMapper;
import com.xiaowu5759.sell.dto.CartDTO;
import com.xiaowu5759.sell.dto.OrderDTO;
import com.xiaowu5759.sell.entity.OrderDetail;
import com.xiaowu5759.sell.entity.OrderMaster;
import com.xiaowu5759.sell.entity.ProductInfo;
import com.xiaowu5759.sell.enums.OrderStatusEnum;
import com.xiaowu5759.sell.enums.PayStatusEnum;
import com.xiaowu5759.sell.enums.ResultEnum;
import com.xiaowu5759.sell.exception.SellException;
import com.xiaowu5759.sell.service.OrderService;
import com.xiaowu5759.sell.service.PayService;
import com.xiaowu5759.sell.service.ProductService;
import com.xiaowu5759.sell.util.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
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

	@Autowired
	private PayService payService;

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
		// 只希望orderDTO里面有orderId
		orderDTO.setOrderId(orderId);
		BeanUtils.copyProperties(orderDTO, orderMaster);
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
	public OrderDTO getOrder(String orderId){
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
		Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList,pageable,orderMasterPage.getTotalElements());
		return orderDTOPage;
	}

	@Override
	public Page<OrderDTO> listAllOrders(Pageable pageable) {
		Page<OrderMaster> orderMasters = orderMasterMapper.findAll(pageable);
		List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasters.getContent());
		return new PageImpl<>(orderDTOList, pageable, orderMasters.getTotalElements());
	}

	@Override
	// todo 抛错中断可能都需要事务，如果两次读写数据库，就更需要
	@Transactional
	public OrderDTO cancelOrder(OrderDTO orderDTO) {
		OrderMaster orderMaster = new OrderMaster();
		// todo 注意对象拷贝的时机，现在我们需要orderDTO的完整状态
//		BeanUtils.copyProperties(orderDTO, orderMaster);
		// 1. 判断订单状态
		if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
			log.error("【取消订单】订单状态不正确,orderId=[{}],orderStatus=[{}]",orderDTO.getOrderId(),orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		// 2. 修改订单状态
		orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult = orderMasterMapper.save(orderMaster);
		if(updateResult == null){
			log.error("【取消订单】更新订单状态失败，orderMaster=[{}]",orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}

		// 3. 返回库存
		if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
			log.error("【取消订单】订单中无商品详情，orderDTO=[{}]",orderDTO);
			throw  new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
		}
		List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(orderDetail -> new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity())).collect(Collectors.toList());
		productService.increaseStock(cartDTOList);

		// 4. 如果已支付，需要退款
		if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
			// todo 退款问题
			payService.refund(orderDTO);
		}
		return orderDTO;
	}

	@Override
	@Transactional
	public OrderDTO finishOrder(OrderDTO orderDTO) {
		// 1. 判断订单状态
		if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
			log.error("【完结订单】订单状态不正确,orderId=[{}],orderStatus=[{}]", orderDTO.getOrderId(),orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}

		// 2. 修改订单状态
		OrderMaster orderMaster = new OrderMaster();
		orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult = orderMasterMapper.save(orderMaster);
		if (updateResult == null){
			log.error("【完结订单】更新失败,orderMaster=[{}]", orderMaster);
			throw  new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}
		return orderDTO;
	}

	@Override
	@Transactional
	public OrderDTO payOrder(OrderDTO orderDTO) {
		// 1. 判断订单状态
		if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
			log.error("【支付订单】订单状态不正确,orderId=[{}],orderStatus=[{}]", orderDTO.getOrderId(),orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		// 2. 判断支付状态
		if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
			log.error("【支付订单】订单支付状态不正确,orderDTO=[{}]", orderDTO);
			throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
		}

		// 3. 修改{订单}、支付状态
		// 这里只修改了支付状态，一个方法就负责一个职责
		OrderMaster orderMaster = new OrderMaster();
		orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult = orderMasterMapper.save(orderMaster);
		if (updateResult == null){
			log.error("【支付订单】更新失败,orderMaster=[{}]", orderMaster);
			throw  new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}

		return orderDTO;
	}
}
