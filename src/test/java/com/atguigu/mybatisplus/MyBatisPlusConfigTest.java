package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * 我batis +配置测试
 *
 * @author DELL_
 * @date 2022/03/31
 */
@SpringBootTest
class MyBatisPlusConfigTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectByPage() {
        /*
            SQL -> SELECT uid,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 LIMIT ?,?
            分页的参数是: 该页索引, 每页中的条数. 从index处开始的后面一页
                比如一页3条, 那么第三页的参数为 8, 3
         */
        Page<User> userPage = new Page<User>(2, 4);
        // 设置条件
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<User>();
        userLambdaQueryWrapper.like(User::getName, "admin");

        // 数据都放在了page里面
        Page<User> page = userMapper.selectPage(userPage, null);
        System.out.println("总页数 -> " + page.getPages());
        System.out.println("总记录数 -> " + page.getTotal());
        System.out.println("是否有下一页 -> " + page.hasNext());
        System.out.println("当前页记录 -> " + page.getRecords());
        System.out.println(page);
    }

    @Test
    void myBatisPlusInterceptor() {

    }
}
