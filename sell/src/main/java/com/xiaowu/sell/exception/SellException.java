package com.xiaowu.sell.exception;

import com.xiaowu.sell.enums.ResultEnum;

/**
 * @author XiaoWu
 * @date 2019/8/1 14:47
 */
public class SellException extends RuntimeException {
	private  Integer code;

	public SellException(ResultEnum resultEnum) {
		super(resultEnum.getMessage());
		this.code = resultEnum.getCode();
	}

	public SellException(Integer code,String message){
		super(message);
		this.code = code;
	}
}
