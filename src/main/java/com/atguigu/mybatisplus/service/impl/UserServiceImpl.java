package com.atguigu.mybatisplus.service.impl;

import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import com.atguigu.mybatisplus.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/*
    ServiceImpl<T, M>
        - T: 这个泛型是指当前需要操作的mapper接口. 对应的就是UserMapper
        - M: 当前需要操作的实体类的类型. User
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {



}
