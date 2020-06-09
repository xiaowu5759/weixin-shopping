package com.xiaowu.sell.form;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
