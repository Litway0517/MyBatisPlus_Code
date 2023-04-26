package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.mapper.EmployeeMapper;
import com.atguigu.mybatisplus.pojo.Department;
import com.atguigu.mybatisplus.pojo.Employee;
import com.atguigu.mybatisplus.pojo.EmployeeVo;
import com.atguigu.mybatisplus.pojo.Job;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
     * 测试MPJWrapper查询
     */
    @Test
    public void testMPJWrapperSelect() {
        /*
            使用selectAs查询
                将其他实体的某个属性添加到主实体中, jobTitle 作为 EmployeeVo实体的成员

            SQL -> SELECT t.employee_id,t.`first_name`,t1.job_title AS jobTitle
                    FROM `employees` t LEFT JOIN jobs t1 ON (t1.job_id = t.`job_id`)
         */
        List<EmployeeVo> employeeVoList = employeeMapper.selectJoinList(EmployeeVo.class, new MPJLambdaWrapper<Employee>()
                .select(Employee::getEmployeeId, Employee::getFirstName)
                .selectAs(Job::getJobTitle, EmployeeVo::getJobTitle)
                .leftJoin(Job.class, Job::getJobId, Employee::getJobId));

        employeeVoList.forEach(System.out::println);
    }

    /**
     * 测试单表查询
     */
    @Test
    public void testJoinSelect() {
        /*
            SQL -> SELECT t.employee_id,t.`first_name`,t.`email`,t.`department_id` FROM `employees` t
         */
        List<Employee> employeeList = employeeMapper.selectJoinList(Employee.class, new MPJLambdaWrapper<Employee>()
                .select(Employee::getEmployeeId, Employee::getFirstName, Employee::getEmail, Employee::getDepartmentId));

        employeeList.forEach(System.out::println);
    }

    /**
     * 测试选择
     */
    @Test
    public void testSelect() {
        /*
            当仅对单表查询时, 应该使用mybatisplus的wrapper, 而不能使用mybatisplus join的wrapper

            SQL -> SELECT employee_id,`first_name`,`phone_number`,`department_id`
                    FROM `employees` WHERE (`department_id` IN (60, 70, 80) OR `phone_number` LIKE '515%')
         */
        List<Employee> employeeList = employeeMapper.selectList(new LambdaQueryWrapper<Employee>()
                .select(Employee::getEmployeeId, Employee::getFirstName, Employee::getPhoneNumber, Employee::getDepartmentId)
                .inSql(Employee::getDepartmentId, "60, 70, 80")
                .or()
                .likeRight(Employee::getPhoneNumber, "515"));

        employeeList.forEach(System.out::println);
    }

}
