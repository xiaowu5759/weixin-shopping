package com.xiaowu5759.sell.vo;

import lombok.Data;

/**
 * http请求返回的最外层对象
 * @author XiaoWu
 * @date 2019/7/30 15:26
 */
@Data
public class ResultVO<T> {

	// 错误码
	private Integer code;

	// 信息
	private String msg;

	// 具体内容
	private T data;
}
