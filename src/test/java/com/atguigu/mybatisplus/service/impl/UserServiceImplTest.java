package com.atguigu.mybatisplus.service.impl;

import com.atguigu.mybatisplus.pojo.User;
import com.atguigu.mybatisplus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;


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

    // 批量添加
    @Test
    public void testBatchInsert() {
        ArrayList<User> userArrayList = new ArrayList<User>();
        for (int i = 0; i <= 10; i++) {
            User user = new User();
            user.setName("lit" + i);
            user.setAge(20 + i);
            user.setEmail("lit" + i + "@163.com");
            userArrayList.add(user);
        }
        boolean b = userService.saveBatch(userArrayList);
        System.out.println("批量添加 -> " + b);
    }

    @Test
    public void testGetCount() {
        long count = userService.count();
        System.out.println("总记录数 -> " + count);
    }


}
