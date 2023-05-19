package com.atguigu.mybatisplus.service.impl;

import com.atguigu.mybatisplus.pojo.Employee;
import com.atguigu.mybatisplus.service.EmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author Litway
 * @version 1.0
 */
@SpringBootTest
public class EmployeeServiceImplTest {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 测试新增一条数据
     */
    @Test
    public void testSave() {
        Employee employee = new Employee();
        employee.setEmployeeId(207);
        employee.setFirstName("Test");
        employee.setLastName("John1");
        employee.setEmail("FHIGHA");
        employee.setPhoneNumber("515.123.8181");
        employee.setHireDate(new Date());
        employee.setJobId("AC_ACCOUNT");
        employee.setSalary(8300.00);
        employee.setManagerId(205);
        employee.setDepartmentId(110);
        employeeService.save(employee);
    }

    /**
     * 测试根据wrapper条件删除数据
     */
    @Test
    public void testRemove() {
        /*
            SQL -> DELETE FROM `employees` WHERE (employee_id = 207 AND `email` = 'FHIGHA')
         */
        boolean flag = employeeService.remove(new LambdaQueryWrapper<Employee>()
                .eq(Employee::getEmployeeId, 207)
                .eq(Employee::getEmail, "FHIGHA"));
        System.out.println(flag);
    }

    /**
     * 测试根据wrapper条件更新数据
     */
    @Test
    public void testUpdate() {
        boolean flag = employeeService.update(new LambdaUpdateWrapper<Employee>()
                .eq(Employee::getEmployeeId, 207)
                .setSql("manager_id = 206"));
        System.out.println(flag);
    }

    /**
     * 测试根据id查询数据
     */
    @Test
    public void testGet() {
        Employee employeeById = employeeService.getById(206);
        System.out.println(employeeById);

        Employee employeeByWrapper = employeeService.getOne(new LambdaQueryWrapper<Employee>()
                .eq(Employee::getEmployeeId, 206));
        System.out.println(employeeByWrapper);
    }

    /**
     * 测试根据wrapper条件查询多条数据
     */
    @Test
    public void testList() {
        List<Employee> employeeList = employeeService.list();
        System.out.println(employeeList);

        List<Employee> employeeListByWrapper = employeeService.list(new LambdaQueryWrapper<Employee>()
                .gt(Employee::getEmployeeId, 196));
        System.out.println(employeeListByWrapper);

        List<Map<String, Object>> maps = employeeService.listMaps();
        System.out.println(maps);

        List<Map<String, Object>> mapsByWrapper = employeeService.listMaps(new LambdaQueryWrapper<Employee>()
                .gt(Employee::getEmployeeId, 196));
        System.out.println(mapsByWrapper);
    }

    /**
     * 测试根据wrapper条件分页查询数据
     */
    @Test
    public void testPage() {
        Page<Employee> page = new Page<>();
        Page<Employee> employeePage = employeeService.page(page);
        System.out.println(employeePage);

        Page<Employee> page2 = new Page<>();
        Page<Employee> employeePageByWrapper = employeeService.page(page2, new LambdaQueryWrapper<Employee>()
                .gt(Employee::getEmployeeId, 196));
        System.out.println(employeePageByWrapper);
    }

    /**
     * 测试根据wrapper条件查询总记录数
     */
    @Test
    public void testCount() {
        /*
            SQL -> SELECT COUNT( * ) FROM `employees`
         */
        long count = employeeService.count();
        System.out.println(count);

        long countByWrapper = employeeService.count(new LambdaQueryWrapper<Employee>()
                .gt(Employee::getEmployeeId, 196));
        System.out.println(countByWrapper);
    }

}