package com.xiaowu.sell.controller.handler;

import com.xiaowu.sell.enums.ResultEnum;
import com.xiaowu.sell.exception.SellException;
import com.xiaowu.sell.util.ExceptionUtils;
import com.xiaowu.sell.util.ResultVOUtils;
import com.xiaowu.sell.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 * @createBy XiaoWu
 * @date 2019/9/4 8:59
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	// 业务异常处理
	@ExceptionHandler(value = SellException.class)
	public ResultVO sellExceptionHandler(SellException e){
		e.printStackTrace();
		// 业务异常，在抛出异常处及时记录log.info
		return ResultVOUtils.error(e);
	}

//	 //在上线时加上，拦截各类异常情况
//	@ExceptionHandler(value = Exception.class)
//	public ResultVO allExceptionHandler(Exception e){
//		// 用log打印出来，就不需要e.printStackTrace
//	    // 抛异常处可以不用记录
//	    log.error(ExceptionUtils.getMessage(e));
//		return ResultVOUtils.error();
//	}
}
