package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.mapper.EmployeeMapper;
import com.atguigu.mybatisplus.pojo.Department;
import com.atguigu.mybatisplus.pojo.Employee;
import com.atguigu.mybatisplus.pojo.EmployeeVo;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author Litway
 * @version 1.0
 */
@SpringBootTest
public class MybatisPlusJoinWrapperTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 测试多表联查
     */
    @Test
    public void testJoinSelect() {
        List<EmployeeVo> employeeVoList = employeeMapper.selectJoinList(EmployeeVo.class, new MPJLambdaWrapper<Employee>()
                .select(Employee::getEmployeeId, Employee::getFirstName, Employee::getEmail, Employee::getDepartmentId)
                .select(Department::getDepartmentId, Department::getDepartmentName)
                .leftJoin(Department.class, Department::getDepartmentId, Employee::getDepartmentId));

        employeeVoList.forEach(System.out::println);
    }

}
