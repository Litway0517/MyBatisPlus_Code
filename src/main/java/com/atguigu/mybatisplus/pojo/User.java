package com.atguigu.mybatisplus.pojo;

import lombok.*;

/**
 * 用户
 *
 * @author DELL_
 * @date 2022/03/21
 */

// NoArgsConstructor注解用于简化实体类开发. 实体类虽然没有无参构造, 但是编译之后class文件中会有
@NoArgsConstructor

// AllArgsConstructor注解用来添加所有参数构造
@AllArgsConstructor

// 分别是加上getter\setter\hashCode方法
@Getter
@Setter
@EqualsAndHashCode
public class User {

    /**
     * id
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 电子邮件
     */
    private String email;



}
