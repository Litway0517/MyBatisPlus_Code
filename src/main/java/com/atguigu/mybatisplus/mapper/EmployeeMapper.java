package com.atguigu.mybatisplus.mapper;

import com.atguigu.mybatisplus.pojo.Employee;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Litway
 * @version 1.0
 */
@Mapper
@DS("slave_1")
public interface EmployeeMapper extends MPJBaseMapper<Employee> {
}