package com.atguigu.mybatisplus.enums;

import lombok.Getter;

/**
 * 性枚举
 *
 * @author DELL_
 * @date 2022/04/01
 */
@Getter
public enum SexEnum {
    MALE(1, "男"),
    FEMALE(2, "女");

    /**
     * 性
     */
    private Integer sex;
    /**
     * 性名字
     */
    private String sexName;

    SexEnum(Integer sex, String sexName) {
        this.sex = sex;
        this.sexName = sexName;
    }
}
