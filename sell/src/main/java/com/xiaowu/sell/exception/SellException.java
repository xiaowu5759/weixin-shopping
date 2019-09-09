package com.xiaowu.sell.exception;

import com.xiaowu.sell.enums.ResultEnum;

/**
 * 服务（业务）异常如“ 账号或密码错误 ”，该异常只做INFO级别的日志记录
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

	public Integer getCode() {
		return code;
	}

	/**
	 * 方便堆栈信息的打印
	 * @return
	 */
	@Override
	public String toString() {
		return "SellException{" +
				"code=" + code +
				"message=" + this.getMessage() +
				'}';
	}
}
