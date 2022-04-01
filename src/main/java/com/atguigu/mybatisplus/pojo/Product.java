package com.atguigu.mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.Version;
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
    // 加上Version注解, 标识此属性对应的表中的字段为版本号
    @Version
    private Integer version;

}
