package com.xiaowu.sell.util;

import com.xiaowu.sell.vo.ResultVO;

/**
 * @author XiaoWu
 * @date 2019/7/30 16:02
 */
public class ResultVOUtils {

	public static ResultVO success(Object object){
		ResultVO resultVO = new ResultVO();
		resultVO.setData(object);
		resultVO.setCode(0);
		resultVO.setMsg("成功");
		return resultVO;
	}

	public static ResultVO success(){
		return success(null);
	}

	public static ResultVO error(Integer code, String msg){
		ResultVO resultVO = new ResultVO();
		resultVO.setMsg(msg);
		resultVO.setCode(code);
		return resultVO;
	}
}
