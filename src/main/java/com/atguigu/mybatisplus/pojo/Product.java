package com.atguigu.mybatisplus.pojo;

import lombok.Data;

/**
 * 产品
 *
 * @author DELL_
 * @date 2022/04/01
 */
// lombok增加getter|setter|toString|无参构造
@Data
public class Product {

    /**
     * id
     */
    private Long id;
    /**
     * 名字
     */
    private String name;
    /**
     * 价格
     */
    private Integer price;

    /**
     * 版本
     */
    private Integer version;

}
