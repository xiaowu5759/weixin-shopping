package com.xiaowu.sell.dao;

import com.xiaowu.sell.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author XiaoWu
 * @date 2019/8/1 13:54
 */
public interface OrderMasterMapper extends JpaRepository<OrderMaster,String> {
	Page<OrderMaster> findByBuyerOpenid(String buyerOpenId, Pageable pageable);

	OrderMaster findByOrderId(String OrderId);
}
