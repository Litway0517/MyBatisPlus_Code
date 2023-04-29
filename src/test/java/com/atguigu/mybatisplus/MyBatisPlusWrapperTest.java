package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.mapper.EmployeeMapper;
import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

// 测试条件构造器
@SpringBootTest
public class MyBatisPlusWrapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 测试其他数据源, 方法上的注解未生效, 但是官网说明支持在方法上实用, mapper层注解生效
     */
    @Test
    // @DS("slave_1")
    public void testDynamicDatasource() {
        List<Employee> employeeList = employeeMapper.selectList(null);
        System.out.println(employeeList);
    }

    /**
     * 主动加上嵌套括号
     */
    @Test
    public void testOther() {
        List<User> users = userMapper.selectList(new LambdaQueryWrapper<User>()
                .select(User::getName, User::getAge)
                .ge(User::getAge, 20)
                .nested(i -> i.like(User::getName, "a").isNotNull(User::getEmail)));
        users.forEach(System.out::println);

    }

    /**
     * 使用lambda查询器 -> 执行更新操作
     */
    @Test
    public void testLambdaUpdateWrapper() {
        /*
            SQL -> UPDATE t_user SET user_name=? WHERE is_deleted=0 AND (user_name LIKE ?
                    AND age > ? AND age < ?)
         */
        String username = "test";
        Integer ageMin = 20;
        Integer ageMax = 30;
        LambdaUpdateWrapper<User> userLambdaUpdateWrapper = new LambdaUpdateWrapper<User>();
        userLambdaUpdateWrapper.like(StringUtils.isNotBlank(username), User::getName, username)
                .ge(ageMin != null, User::getAge, ageMin)
                .le(ageMax != null, User::getAge, ageMax);
        userLambdaUpdateWrapper.set(User::getName, "admin");
        // 这里面在更新的时候, 会把主键也改掉, 出现了问题
        int update = userMapper.update(null, userLambdaUpdateWrapper);
        System.out.println("影响行数 -> " + update);
    }

    /**
     * 使用lambda查询器 -> lambda的出现是为了避免将数据库表的字段名写错 是通过访问实体类所对应的字段名实现的
     */
    @Test
    public void testLambdaQueryWrapper() {
        // 条件: 名称中包含b字母,  年龄 20-30
        /*
            SQL -> SELECT uid,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0
            AND (user_name LIKE ? AND age > ? AND age < ?)
         */
        // 条件为空, 下面的查询sql中like语句不会拼接上
        String username = "";
        Integer ageMin = 20;
        Integer ageMax = 30;
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<User>();
        userLambdaQueryWrapper.like(StringUtils.isNotBlank(username), User::getName, username)
                .gt(ageMin != null, User::getAge, ageMin)
                .lt(ageMax != null, User::getAge, ageMax);
        List<User> users = userMapper.selectList(userLambdaQueryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 通过QueryWrapper, 带条件的查询, 当条件为真时才会拼接SQL
     */
    @Test
    public void testSelectByCondition2() {
        // 条件: 名称中包含b字母,  年龄 20-30
        /*
            SQL -> SELECT uid,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0
                    AND (user_name LIKE ? AND age > ? AND age < ?)
         */
        String username = "b";
        Integer ageMin = 20;
        Integer ageMax = 30;
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>();
        // like()方法第一个参数为boolean, 如果这个条件表达式为真, 那么就拼接该SQL片段, 从而简化if判断
        userQueryWrapper.like(StringUtils.isNotBlank(username), "user_name", username)
                .gt(ageMin != null, "age", ageMin)
                .lt(ageMax != null, "age", ageMax);
        List<User> users = userMapper.selectList(userQueryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 通过QueryWrapper, 带条件的查询
     */
    @Test
    public void testSelectByCondition() {
        // 条件: 名称中包含b字母,  年龄 20-30
        /*
            SQL -> SELECT uid,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0
                    AND (user_name LIKE ? AND age > ? AND age < ?)
         */
        String username = "b";
        Integer ageMin = 20;
        Integer ageMax = 30;
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>();
        if (StringUtils.isNotBlank(username)) {
            userQueryWrapper.like("user_name", username);
        }
        // 简单判断
        if (ageMin != null) {
            userQueryWrapper.gt("age", ageMin);
        }
        if (ageMax != null) {
            userQueryWrapper.lt("age", ageMax);
        }
        List<User> users = userMapper.selectList(userQueryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 通过UpdateWrapper, 更新操作, 注意and条件在使用的时候是主动加上括号的
     */
    @Test
    public void testUpdateByUpdateWrapper() {
        // 将用户名中包含有a并且(年龄大于20或邮箱为null)的用户信息修改
        /*
            SQL -> UPDATE t_user SET user_name=?,email=? WHERE is_deleted=0
                AND (user_name LIKE ? AND (age > ? OR email IS NULL))
         */
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<User>();
        userUpdateWrapper.like("user_name", "a")
                .and(i -> i.gt("age", 20).or().isNull("email"));
        userUpdateWrapper.set("user_name", "小蓝");
        userUpdateWrapper.set("email", "abc@atguigu.com");

        // 使用的是updateWrapper所以不传入实体类
        userMapper.update(null, userUpdateWrapper);
    }

    /**
     * 通过QueryWrapper, 子查询实例, inSql方法
     */
    @Test
    public void testQueryWrapper2() {
        /*
            SQL ->
                SELECT uid,user_name AS name,age,email,is_deleted FROM t_user
                WHERE is_deleted=0 AND (uid IN (select uid from t_user where is_deleted = 0 and uid <= 100))
         */
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>();
        userQueryWrapper.inSql("uid",
                "select uid from t_user where is_deleted = 0 and uid <= 100");
        List<User> users = userMapper.selectList(userQueryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 通过QueryWrapper, 查询部分信息, 使用Map进行保存
     */
    @Test
    public void testSelectByMap() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>();
        userQueryWrapper.select("user_name as name", "age", "email");
        List<Map<String, Object>> list = userMapper.selectMaps(userQueryWrapper);
        list.forEach(System.out::println);
    }

    /**
     * 通过QueryWrapper, 实现更新
     */
    @Test
    public void testUpdate2() {
        // 将用户名中包含有a并且(年龄大于20或邮箱为null)的用户信息修改
        /*
            and()方法|or()方法中的lambda表达式会优先执行

            下面的and方法中的i就是QueryWrapper<User>类型的对象
            SQL引擎: 会优先执行加上小括号的SQL片段
            UPDATE t_user SET user_name=?, email=? WHERE is_deleted=0 AND (user_name LIKE ? AND (age > ? OR email IS NULL))
            ==> Parameters: 小红(String), test@atguigu.com(String), %a%(String), 20(Integer)
         */
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>();
        userQueryWrapper
                .like("user_name", "a")
                // 在and方法里面使用lambda, i参数实际上就是消费者接口的泛型
                .and(i -> i.gt("age", 20).or().isNull("email"));
        User user = new User();
        user.setName("小红");
        user.setEmail("test@atguigu.com");
        int update = userMapper.update(user, userQueryWrapper);
        System.out.println("影响行数 -> " + update);

    }

    /**
     * 通过QueryWrapper, 实现更新
     */
    @Test
    public void testUpdate() {
        // 将(年龄大于20并且用户名中包含有a)或邮箱为null的用户信息修改
        /*
            通过QueryWrapper来实现更新. 从QueryWrapper中查询符合条件的数据, 然后使用entity进行更新
                - entity用来设置新的数据
                - QueryWrapper用来设置查询条件

            UPDATE t_user SET user_name=?, email=? WHERE is_deleted=0 AND (age > ? AND user_name LIKE ? OR email IS NULL)
            ==> Parameters: 小明(String), 小明@atguigu.com(String), 20(Integer), %a%(String)
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

    // 通过QueryWrapper 带条件删除
    @Test
    public void testDelete() {
        // 条件 -> 删除邮箱为null的用户信息
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>();
        userQueryWrapper.isNull("email");
        int delete = userMapper.delete(userQueryWrapper);
        System.out.println("影响行数 -> " + delete);
    }

    /**
     * 通过QueryWrapper, 带条件查询
     */
    @Test
    public void testQuery2() {
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


    /**
     * 通过QueryWrapper, 带条件的查询, 能够改成使用lambda查询, 避免写错字段
     */
    @Test
    public void testQuery() {
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
