package com.xiaowu5759.sell.form;

import lombok.Data;

/**
 * 类目表单
 * @author xiaowu
 * @date 2020/6/2 14:17
 */
@Data
public class CategoryForm {

    /** 类目主键 */
    private Integer categoryId;

    /** 类目名字 */
    private String categoryName;

    /** 类目编号 */
    private Integer categoryType;
}
