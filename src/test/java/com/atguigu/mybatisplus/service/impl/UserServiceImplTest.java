package com.atguigu.mybatisplus.service.impl;

import com.atguigu.mybatisplus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * 用户服务Impl测试
 *
 * @author DELL_
 * @date 2022/03/27
 */
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void testGetCount() {
        long count = userService.count();
        System.out.println("总记录数 -> " + count);
    }


}
