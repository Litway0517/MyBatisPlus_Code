package com.atguigu.mybatisplus.pojo;

import com.atguigu.mybatisplus.enums.SexEnum;
import com.baomidou.mybatisplus.annotation.*;
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

/*
    TableName("xx")注解用于手动映射表名.
    因为在使用通用mappers|通用Service时传入的就是User对象, 框架会认为表就叫做user. 现在表名改成了t_user, 所以进行手动映射.
 */
@TableName("t_user")
public class User {

    /**
     * id
     */
    /*
        将id属性对应的字段作为SQL的主键
        参数:
            - value: 用于主动映射实体类的成员变量名称和数据库表字段的名称. 如果一样就可以不写
                使用注解的话, 如果只有一个参数值, 那么默认就是给value参数赋值的.
            - type: 用来设置表中的主键字段的递增方式, 有自增 雪花算法 手动输入 等几种方式
     */
//    @TableId(value = "uid")
    @TableId(value = "uid", type = IdType.AUTO)
    private Long uid;
    /**
     * 用户名
     */
    // TableField注解和TableId注解差不多, 只不过TableId是用来映射表中主键字段与成员变量的关系的
    @TableField("user_name")
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 电子邮件
     */
    private String email;

    /**
     * 性
     */
    private SexEnum sex;

    /**
     * 被删除
     */
    @TableLogic
    private Integer isDeleted;



}
