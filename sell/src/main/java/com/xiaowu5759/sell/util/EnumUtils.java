package com.xiaowu5759.sell.util;

import com.xiaowu5759.sell.enums.CodeEnum;

/**
 * @author xiaowu
 * @date 2020/6/1 11:34
 */
public class EnumUtils {
    /* 方法中适用泛型，需要对泛型进行说明
    * T 继承 CodeEnum
    * getEnumConstants 返回该枚举类型的所有元素，如果Class对象不是枚举类型，则返回null。*/
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass){
        for(T each: enumClass.getEnumConstants()){
            if(code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}
