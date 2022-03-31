package com.atguigu.mybatisplus.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 我batis +配置
 *
 * @author DELL_
 * @date 2022/03/31
 */
// 增加MapperScan("xxx")注解 -> 扫描xx包下面的mapper接口文件
@MapperScan("com.atguigu.mybatisplus.mapper")

@Configuration
public class MyBatisPlusConfig {

}
