package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MyBatisPlusWrapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testWrapper() {
        // 创建条件构造器
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>();
        /*
            设置条件 -> 查询用户名包含a, 年龄在20-30之间, 邮箱信息不为null的用户信息
            - column: 是数据库表的字段名称, 而不是实体类的属性名
         */
        userQueryWrapper
                .like("user_name", "a")
                .between("age", 20, 30)
                .isNotNull("email");
        List<User> users = userMapper.selectList(userQueryWrapper);
        users.forEach(System.out::println);
    }
}
