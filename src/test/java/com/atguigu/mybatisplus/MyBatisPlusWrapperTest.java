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

    // 更新东座
    @Test
    public void testUpdate2() {
        // 将用户名中包含有a并且(年龄大于20或邮箱为null)的用户信息修改
        /*
            and()方法|or()方法中的lambda表达式会优先执行

            下面的and方法中的i就是QueryWrapper<User>类型的e对象
            SQL引擎: 会优先执行加上小括号的SQL片段
            UPDATE t_user SET user_name=?, email=? WHERE is_deleted=0 AND (user_name LIKE ? AND (age > ? OR email IS NULL))
            ==> Parameters: 小红(String), test@atguigu.com(String), %a%(String), 20(Integer)
         */
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>();
        userQueryWrapper
                .like("user_name", "a")
                .and(i -> i.gt("age", 20).or().isNull("email"));
        User user = new User();
        user.setName("小红");
        user.setEmail("test@atguigu.com");
        int update = userMapper.update(user, userQueryWrapper);
        System.out.println("影响行数 -> " + update);

    }

    // 更新动作 -> 通过QueryWrapper来实现更新.
    @Test
    public void testUpdate() {
        // 将(年龄大于20并且用户名中包含有和)或邮箱为null的用户信息修改
        /*
            通过QueryWrapper来实现更新. 从QueryWrapper中查询符合条件的数据, 然后使用entity进行更新
                - entity用来设置新的数据
                - QueryWrapper用来设置查询条件
         */
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>();
        userQueryWrapper.gt("age", 20)
                .like("user_name", "a")
                .or()
                .isNull("email");
        User user = new User();
        user.setName("小明");
        user.setEmail("小明@atguigu.com");
        int update = userMapper.update(user, userQueryWrapper);
        System.out.println("影响行数 -> " + update);
    }

    // 带条件删除
    @Test
    public void testDeleteWrapper() {
        // 条件 -> 删除邮箱为null的用户信息
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>();
        userQueryWrapper.isNull("email");
        int delete = userMapper.delete(userQueryWrapper);
        System.out.println("影响行数 -> " + delete);
    }

    // 带条件查询
    @Test
    public void testQueryWrapper() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>();
        /*
            条件 -> 查询结果按照年龄降序排序, 年龄相同按照id

            SQL -> SELECT uid,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0
                    ORDER BY age DESC,uid ASC
         */
        userQueryWrapper.orderByDesc("age")
                .orderByAsc("uid");
        List<User> list = userMapper.selectList(userQueryWrapper);
        list.forEach(System.out::println);
    }


    // 带条件的查询
    @Test
    public void testWrapper() {
        // 创建条件构造器
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>();
        /*
            设置条件 -> 查询用户名包含a, 年龄在20-30之间, 邮箱信息不为null的用户信息
            链式编程, 因为每一个条件返回的都是QueryWrapper, 所以还是能够调用其他方法设置条件
            - column: 是数据库表的字段名称, 而不是实体类的属性名

            SQL -> SELECT uid,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0
                    AND (user_name LIKE ? AND age BETWEEN ? AND ? AND email IS NOT NULL)
                    ==> Parameters: %a%(String), 20(Integer), 30(Integer)
         */
        userQueryWrapper
                .like("user_name", "a")
                .between("age", 20, 30)
                .isNotNull("email");
        List<User> users = userMapper.selectList(userQueryWrapper);
        users.forEach(System.out::println);
    }
}
