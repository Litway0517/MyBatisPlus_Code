package com.atguigu.mybatisplus;


import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Mybatis +测试
 *
 * @author DELL_
 * @date 2022/03/24
 */
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

    @Test
    public void testInsert() {
        // SQL -> INSERT INTO user ( id, name, age, email ) VALUES ( ?, ?, ?, ? )
        User user = new User();
        user.setName("lit");
        user.setAge(92);
        user.setEmail("lit@163.com");
        int insert = userMapper.insert(user);
        System.out.println("result: " + insert);
        System.out.println("打印插入该条数据后得到的主键(这里使用的是雪花算法生成的id, 并不是表中的id): " + user.getId());
    }

}
