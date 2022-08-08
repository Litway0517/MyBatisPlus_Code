package com.atguigu.mybatisplus.mapper;


import com.atguigu.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

// 继承自MyBatisPlus提供的通用mapper接口 BaseMapper. 需要指出改mapper接口操作的数据类型 即User类型
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 得到用户id
     *
     * @param id id
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> getUserById(Long id);

    /**
     * 通过年龄查询用户信息并分页! 主要是分页功能结合自定义的SQL
     * 如若想在自定义SQL中实现分页功能, 那么mapper的这个方法的接口的第一个参数必须是Page. 返回值为Page<User>
     *
     * @param page 页面. 第一个参数必须是Page对象, 这样才能借用MyBatisPlus框架的分页功能
     * @param age  年龄
     * @return {@link Page}<{@link User}> 返回值的类型也是Page<User> 泛型为实体类对象
     */
    Page<User> selectPageVo(@Param("page") Page<User> page, @Param("age") Integer age);



}
