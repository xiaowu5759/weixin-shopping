package com.xiaowu5759.sell.util;

import java.util.Random;

/**
 * @author XiaoWu
 * @date 2019/8/1 14:59
 */
public class KeyUtils {

	/**
	 * 生成唯一的主键
	 * 格式：时间+随机数
	 * @return
	 */
	public static String genUniqueKey(){
		Random random = new Random();
		// 生成六位随机数
		Integer number = random.nextInt(900000) + 100000;
		return System.currentTimeMillis() + String.valueOf(number);
	}
}
