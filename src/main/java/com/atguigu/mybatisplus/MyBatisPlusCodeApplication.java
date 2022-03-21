package com.atguigu.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

// 增加MapperScan("xxx")注解 -> 扫描xx包下面的mapper接口文件
@MapperScan("com.atguigu.mybatisplus.mapper")
public class MyBatisPlusCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBatisPlusCodeApplication.class, args);
    }

}
