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

/*
    @Data注解会生成 无参构造\getter\setter\hashCode\toString方法. 注意没有有参构造
    @Data注解和@AllArgsConstructor注解一起使用时, 会生成全参数构造方法\getter\setter\hashCode\toString
        但是不会有无参构造, 无参构造是默认的
 */
@Data
public class User {

    /**
     * id
     */
    private Long id;
    /**
     * 用户名
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 电子邮件
     */
    private String email;



}
