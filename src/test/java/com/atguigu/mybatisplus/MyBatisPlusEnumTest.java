package com.atguigu.mybatisplus;


import com.atguigu.mybatisplus.enums.SexEnum;
import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyBatisPlusEnumTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsert() {
        /*
            SQL ->  INSERT INTO t_user ( user_name, age, sex )
                    VALUES ( 'admin', 29, 'MALE' );
            显然这里面的值是不正确的, 因为存的是MALE. 需要使用在sex属性上@EnumValue注解
         */
        User user = new User();
        user.setName("admin");
        user.setAge(29);
        user.setSex(SexEnum.MALE);
        int insert = userMapper.insert(user);
        System.out.println("影响行数 -> " + insert);
    }

}
