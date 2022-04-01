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

    // 自定义的SQL语句 搭配 MybatisPlus的分页插件
    @Test
    public void testSelectPageVo() {
        /*
            SQL -> select uid, user_name, age, email, is_deleted from `t_user` where is_deleted = '0'
                    and age > ? LIMIT ?,?

         */
        // long current参数是当前页, size是每页中记录的数目
        Page<User> userPage = new Page<User>(2, 3);
        // 传入参数
        Page<User> pageVo = userMapper.selectPageVo(userPage, 20);
        System.out.println("总页数 -> " + pageVo.getPages());
        System.out.println("总记录数 -> " + pageVo.getTotal());
        System.out.println("是否有下一页 -> " + pageVo.hasNext());
        System.out.println("当前页记录 -> " + pageVo.getRecords());
        System.out.println(pageVo);
    }


    // MybatisPlus的分页功能 搭配 LambdaQueryWrapper查询器
    @Test
    public void testSelectByPage() {
        /*
            SQL -> SELECT uid,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 LIMIT ?,?
            分页的参数是: 该页索引, 每页中的条数. 从index处开始的后面一页
                比如一页3条, 那么第三页的参数为 8, 3
         */
        Page<User> userPage = new Page<User>(2, 3);
        // 设置条件
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<User>();
        userLambdaQueryWrapper.like(User::getName, "admin");

        // 数据都放在了page里面 本次查询不使用条件
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
