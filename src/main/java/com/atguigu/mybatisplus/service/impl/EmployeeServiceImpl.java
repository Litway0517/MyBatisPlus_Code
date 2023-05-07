package com.atguigu.mybatisplus.service.impl;

import com.atguigu.mybatisplus.mapper.EmployeeMapper;
import com.atguigu.mybatisplus.pojo.Employee;
import com.atguigu.mybatisplus.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Litway
 * @version 1.0
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
