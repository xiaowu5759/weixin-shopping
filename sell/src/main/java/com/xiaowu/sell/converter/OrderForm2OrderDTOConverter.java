package com.xiaowu.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaowu.sell.dto.OrderDTO;
import com.xiaowu.sell.entity.OrderDetail;
import com.xiaowu.sell.enums.ResultEnum;
import com.xiaowu.sell.exception.SellException;
import com.xiaowu.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XiaoWu
 * @date 2019/8/2 16:29
 */
@Slf4j
public class OrderForm2OrderDTOConverter {
	public static OrderDTO convert(OrderForm orderForm){
		Gson gson = new Gson();
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setBuyerName(orderForm.getName());
		orderDTO.setBuyerPhone(orderForm.getPhone());
		orderDTO.setBuyerAddress(orderForm.getAddress());
		orderDTO.setBuyerOpenid(orderForm.getOpenid());
		// todo 转换、连接、获取的时候可能出错
		List<OrderDetail> orderDetails = new ArrayList<>();
		try{
			orderDetails = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>(){}.getType());
		}catch (Exception ex){
			log.error("【对象转换】错误,String=[{}]", orderForm.getItems());
			throw new SellException(ResultEnum.PARAM_ERROR);
		}

		orderDTO.setOrderDetailList(orderDetails);
		return orderDTO;

	}
}
