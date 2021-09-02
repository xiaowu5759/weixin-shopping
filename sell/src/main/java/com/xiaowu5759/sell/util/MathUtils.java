package com.xiaowu5759.sell.util;

/**
 * @author xiaowu
 * @date 2020/5/28 16:37
 */
public class MathUtils {

    private static final Double MONEY_RANGE = 0.01;

    /**
     * 比较2个金额是否相等
     * @param d1
     * @param d2
     * @return
     */
    public static boolean equalsMoney(Double d1, Double d2){
        Double result = Math.abs(d1 - d2);
        return result < MONEY_RANGE;
    }
}
