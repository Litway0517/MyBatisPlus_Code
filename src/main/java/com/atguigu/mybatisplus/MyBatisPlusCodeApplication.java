package com.atguigu.mybatisplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

// 接口扫描的MapperScan注解移动到了配置类上 因为已经有了配置类就不在启动类上扫描了
public class MyBatisPlusCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBatisPlusCodeApplication.class, args);
    }

}
