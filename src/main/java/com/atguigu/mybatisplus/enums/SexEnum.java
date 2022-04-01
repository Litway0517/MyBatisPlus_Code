package com.atguigu.mybatisplus.enums;

/**
 * 性枚举
 *
 * @author DELL_
 * @date 2022/04/01
 */
public enum SexEnum {
    ;

    /**
     * 性
     */
    private Integer sex;
    /**
     * 性名字
     */
    private String sexName;

    /**
     * 获得性
     *
     * @return {@link Integer}
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 集合性
     *
     * @param sex 性
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获得性名字
     *
     * @return {@link String}
     */
    public String getSexName() {
        return sexName;
    }

    /**
     * 设置性名
     *
     * @param sexName 性名字
     */
    public void setSexName(String sexName) {
        this.sexName = sexName;
    }
}
