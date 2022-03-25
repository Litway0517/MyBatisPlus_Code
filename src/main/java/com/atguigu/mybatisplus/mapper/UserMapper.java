package com.atguigu.mybatisplus.mapper;


import com.atguigu.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
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



}
