package com.xiaowu.sell.util;

import com.xiaowu.sell.enums.ResultEnum;
import com.xiaowu.sell.exception.SellException;
import com.xiaowu.sell.vo.ResultVO;

/**
 * @author XiaoWu
 * @date 2019/7/30 16:02
 */
public class ResultVOUtils {

	public static <T> ResultVO<T> success(T object){
		ResultVO<T> resultVO = new ResultVO<>();
		resultVO.setData(object);
		resultVO.setCode(0);
		resultVO.setMsg("成功");
		return resultVO;
	}

	public static ResultVO success(){
		return success(null);
	}

	public static ResultVO error(){
		return error(ResultEnum.SYSTEM_EXCEPTION);
	}

	public static ResultVO error(ResultEnum resultEnum){
		ResultVO resultVO = new ResultVO();
		resultVO.setMsg(resultEnum.getMessage());
		resultVO.setCode(resultEnum.getCode());
		return resultVO;
	}

	public static ResultVO error(SellException sellException) {
		ResultVO resultVO = new ResultVO();
		resultVO.setMsg(sellException.getMessage());
		resultVO.setCode(sellException.getCode());
		return resultVO;
	}
}
