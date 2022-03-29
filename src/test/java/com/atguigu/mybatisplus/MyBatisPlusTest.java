package com.atguigu.mybatisplus;


import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MybatisPlus测试
 *
 * @author DELL_
 * @date 2022/03/24
 */
@SpringBootTest
public class MyBatisPlusTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testWrapper() {
        // 了解Wrapper的继承体系
        QueryWrapper<User> userWrapper = new QueryWrapper<User>();
        userMapper.selectList(userWrapper);
    }

    @Test
    // 测试自定义的查询方法
    public void testSelectBySelf() {
        // 最重要的是要说明, MyBatisPlus框架只做增强不做修改. 所以开发人员仍然能够自己写接口并编写映射文件
        Map<String, Object> userById = userMapper.getUserById(1L);
        System.out.println("自定义的mapper方法 -> " + userById);
    }

    @Test
    // 查询
    public void testSelect() {
        // 根据id查询 -> SELECT id,name,age,email FROM user WHERE id=?
        User user = userMapper.selectById(1L);
        System.out.println("根据id查询 -> " + user);

        // 根据ids批量查询 -> SELECT id,name,age,email FROM user WHERE id IN ( ? , ? , ? )
        List<Long> longs = Arrays.asList(1L, 2L, 3L);
        List<User> users = userMapper.selectBatchIds(longs);
        users.forEach(System.out::println);

        // 根据map集合中的条件来查询 SQL -> SELECT id,name,age,email FROM user WHERE name = ? AND age = ?
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("name", "Jack");
        map.put("age", "20");
        List<User> users1 = userMapper.selectByMap(map);
        users1.forEach(System.out::println);

        // 查询所有
        List<User> users2 = userMapper.selectList(null);
        users2.forEach(System.out::println);
    }

    @Test
    // 修改方法
    public void testUpdate() {
        // SQL -> UPDATE user SET name=?, email=? WHERE id=?
        User user = new User();
        user.setUid(1507344259685613569L);
        user.setName("lit");
        user.setEmail("test@163.com");
        int i = userMapper.updateById(user);
        System.out.println("result -> " + i);
    }

    @Test
    // 测试删除
    public void testDelete() {
        // SQL -> DELETE FROM user WHERE id=?
        int i = userMapper.deleteById(1507301491378589697L);
        System.out.println(i);

        // 通过Map集合删除. SQL -> DELETE FROM user WHERE name = ? AND age = ?
        HashMap<String, Object> map = new HashMap<String, Object>();
        // 存放删除的条件. 字段名 - 字段值
        map.put("name", "lit");
        map.put("age", "100");
        int i1 = userMapper.deleteByMap(map);
        System.out.println("result -> " + i1);

        // 通过集合删除. SQL -> DELETE FROM user WHERE id IN ( ? , ? , ? )
        // Arrays.asList方法能够将传过来的参数形成一个集合
        List<Long> longs = Arrays.asList(1L, 2L, 3L);
        int i2 = userMapper.deleteBatchIds(longs);
        System.out.println("result -> " + i2);

    }

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
        // MybatisPlus默认使用雪花算法生成主键id, 但是如果手动设置id值的话就不会再生成了
        // user.setUid(100L);
        user.setName("lit");
        user.setAge(92);
        user.setEmail("lit@163.com");
        int insert = userMapper.insert(user);
        System.out.println("result: " + insert);
        System.out.println("打印插入该条数据后得到的主键(这里使用的是雪花算法生成的id, 并不是表中的id): " + user.getUid());
    }

}
