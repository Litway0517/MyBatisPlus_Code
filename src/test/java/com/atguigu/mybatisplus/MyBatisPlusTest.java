package com.atguigu.mybatisplus;


import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MyBatisPlusTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectList() {
        // Wrapper是条件构造器
        // 通过条件构造器去查询一个list, 若没有条件, 则可以设置null为参数
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

}
