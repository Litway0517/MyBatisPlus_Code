package com.atguigu.mybatisplus.mapper;


import com.atguigu.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

// 继承自MyBatisPlus提供的通用mapper接口 BaseMapper. 需要指出改mapper接口操作的数据类型 即User类型
@Mapper
public interface UserMapper extends BaseMapper<User> {




}
