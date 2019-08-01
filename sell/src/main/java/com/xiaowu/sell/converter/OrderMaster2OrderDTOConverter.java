package com.xiaowu.sell.converter;

import com.xiaowu.sell.dto.OrderDTO;
import com.xiaowu.sell.entity.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author XiaoWu
 * @date 2019/8/1 17:16
 */
public class OrderMaster2OrderDTOConverter {

	public static OrderDTO convert(OrderMaster orderMaster){
		OrderDTO orderDTO = new OrderDTO();
		BeanUtils.copyProperties(orderMaster, orderDTO);
		return orderDTO;
	}

	public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){
		return orderMasterList.stream().map(orderMaster -> convert(orderMaster)).collect(Collectors.toList());
	}

}
