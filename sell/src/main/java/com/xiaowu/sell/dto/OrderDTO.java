package com.xiaowu.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xiaowu.sell.entity.OrderDetail;
import com.xiaowu.sell.enums.OrderStatusEnum;
import com.xiaowu.sell.enums.PayStatusEnum;
import com.xiaowu.sell.util.EnumUtils;
import com.xiaowu.sell.util.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author XiaoWu
 * @date 2019/8/1 14:35
 */
@Data
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

	private String orderId;

	// 买家名字
	private String buyerName;

	//买家电话
	private String buyerPhone;

	// 买家地址
	private String buyerAddress;

	// 买家微信openid
	private String buyerOpenid;

	// 订单总金额
	private BigDecimal orderAmount;

	// 订单状态，默认0新下单
	private Integer orderStatus = OrderStatusEnum.NEW.getCode();

	// 支付状态，默认0未支付
	private Integer payStatus = PayStatusEnum.WAIT.getCode();

	// 创建时间
	@JsonSerialize(using = Date2LongSerializer.class)
	private Date createTime;

	// 更新时间
	@JsonSerialize(using = Date2LongSerializer.class)
	private Date updateTime;

	private List<OrderDetail> orderDetailList;

	@JsonIgnore
	public OrderStatusEnum getOrderStatusEnum(){
		return EnumUtils.getByCode(orderStatus, OrderStatusEnum.class);
	}

	public PayStatusEnum getPayStatusEnum(){
		return EnumUtils.getByCode(payStatus, PayStatusEnum.class);
	}
}
