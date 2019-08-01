package com.xiaowu.sell.dao;

import com.xiaowu.sell.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author XiaoWu
 * @date 2019/8/1 13:55
 */
public interface OrderDetailMapper extends JpaRepository<OrderDetail,String> {
	List<OrderDetail> findByOrderId(String OrderId);
}
