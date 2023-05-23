package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.mapper.DepartmentMapper;
import com.atguigu.mybatisplus.mapper.EmployeeMapper;
import com.atguigu.mybatisplus.pojo.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 测试MPJLambdaWrapper分页查询, 并根据员工薪水升序排序
     */
    @Test
    public void testMPJLambdaWrapperSelectJoinPageOrderBy() {
        Page<EmployeeVo> page = new Page<>();
        Page<EmployeeVo> employeeVoPage = employeeMapper.selectJoinPage(page, EmployeeVo.class, new MPJLambdaWrapper<Employee>()
                .selectAll(Employee.class)
                .select(Job::getJobTitle)
                .leftJoin(Job.class, Job::getJobId, Employee::getJobId)
                .orderBy(true, true, Employee::getSalary));

        employeeVoPage.getRecords().forEach(System.out::println);
    }

    /**
     * 测试MPJLambdaWrapper分页查询, 不包含一对多
     */
    @Test
    public void testMPJLambdaWrapperSelectJoinPage() {
        /*
            SQL -> SELECT COUNT(*) AS total FROM `employees` t
                   SELECT t.employee_id,t.`first_name`,t1.job_title
                   FROM `employees` t LEFT
                   JOIN jobs t1 ON (t1.job_id = t.`job_id`) LIMIT 10
         */
        Page<EmployeeVo> page = new Page<>();
        Page<EmployeeVo> employeeVoPage = employeeMapper.selectJoinPage(page, EmployeeVo.class, new MPJLambdaWrapper<Employee>()
                .select(Employee::getEmployeeId, Employee::getFirstName)
                .select(Job::getJobTitle)
                .leftJoin(Job.class, Job::getJobId, Employee::getJobId));

        employeeVoPage.getRecords().forEach(System.out::println);
    }

    /**
     * 测试Lambda子查询, 这里采用的是硬编码方式
     */
    @Test
    public void testLambdaWrapperSelectSub() {
        /*
            SQL -> SELECT employee_id,`first_name`
                    FROM `employees`
                    WHERE (`salary` > (select salary from employees where email = 'VPATABAL'))
         */
        List<Employee> employeeList = employeeMapper.selectList(new LambdaQueryWrapper<Employee>()
                .select(Employee::getEmployeeId, Employee::getFirstName)
                .gtSql(Employee::getSalary, "select salary from employees where email = 'VPATABAL'")
        );

        employeeList.forEach(System.out::println);
    }

    /**
     * 测试MPJLambdaWrapper分页查询, 一对多查询
     * 分页部门中存在的员工, DepartmentVo中嵌套List<Employee>
     */
    @Test
    public void testMPJWrapperSelectPageCollection() {
        Page<DepartmentVo> page = new Page<>();
        Page<DepartmentVo> departmentVoPage = departmentMapper.selectJoinPage(page, DepartmentVo.class, new MPJLambdaWrapper<Department>()
                .selectAsClass(Department.class, DepartmentVo.class)
                .selectCollection(DepartmentVo::getEmployeeList, map -> map
                        .id(Employee::getEmployeeId, Employee::getEmployeeId)
                        .result(Employee::getFirstName))
                .leftJoin(Employee.class, Employee::getDepartmentId, Department::getDepartmentId)
        );

        departmentVoPage.getRecords().forEach(System.out::println);
    }

    /**
     * 测试MPJLambdaWrapper记录数量查询, 查询符合条件的记录数
     */
    @Test
    public void testMPJWrapperSelectCount() {
        /*
            查询60部门中的员工数量
            SQL -> SELECT COUNT( t.department_id )
                    FROM departments t
                    LEFT JOIN `employees` t1 ON (t1.`department_id` = t.department_id)
                    WHERE (t.department_id = '60')
         */
        Long count = departmentMapper.selectJoinCount(new MPJLambdaWrapper<Department>()
                .select(Department::getDepartmentId)
                .leftJoin(Employee.class, Employee::getDepartmentId, Department::getDepartmentId)
                .eq(Department::getDepartmentId, "60"));

        System.out.println(count);
    }

    /**
     * 测试MPJLambdaWrapper分页查询, 需要在mybatisplus中配置分页插件
     */
    @Test
    public void testMPJWrapperSelectPage() {
        /*
            SQL -> SELECT t.department_id,t.department_name,t.manager_id,t.location_id FROM departments t LIMIT 10
         */
        Page<DepartmentVo> page = new Page<>();
        Page<DepartmentVo> departmentVoPage = departmentMapper.selectJoinPage(page, DepartmentVo.class,
                new MPJLambdaWrapper<Department>()
                        .selectAsClass(Department.class, DepartmentVo.class));

        departmentVoPage.getRecords().forEach(System.out::println);
    }

    /**
     * 测试MPJLambdaWrapper中根据封装对象属性的查询方法
     * 假设User表中有10个字段, 而业务只需要其中的某3个, 使用mybatis-plus提供的select查询时需要一个属性一个属性填入很不优雅
     * 现在我们可以用selectAsClass(User.class, UserVo.class)
     * 即可按所需的UserVo返回, 前提是UserVo.class中的属性必须是User.class中存在的
     */
    @Test
    public void testSelectAsClass() {
        /*
            SQL -> SELECT t.department_id,t.department_name,t.manager_id,t.location_id,
                    t1.employee_id,t1.`first_name`
                    FROM departments t
                    LEFT JOIN `employees` t1 ON (t1.employee_id = t.manager_id)
                    WHERE (t.department_id = '60')
         */
        List<DepartmentVo> departmentVoList = departmentMapper.selectJoinList(DepartmentVo.class, new MPJLambdaWrapper<Department>()
                .selectAsClass(Department.class, DepartmentVo.class)
                .selectAssociation(DepartmentVo::getManager, map -> map
                        .id(Employee::getEmployeeId)
                        .result(Employee::getFirstName))
                .leftJoin(Employee.class, Employee::getEmployeeId, Department::getManagerId)
                .eq(Department::getDepartmentId, "60"));

        departmentVoList.forEach(System.out::println);
    }

    /**
     * 测试MPJLambdaWrapper一对一查询, 一对多查询, 连接同一张表两次
     * DepartmentVo中嵌套Employee部门管理员, 同时嵌套List<Employee>部门员工
     */
    @Test
    public void testSelectJoinAssociation() {
        /*
            SQL -> SELECT t.department_id,t.department_name,t.manager_id,t.location_id,
                    t1.employee_id,t1.`first_name`,t2.employee_id AS joina_employee_id,
                    t2.`first_name` AS joina_first_name
                    FROM departments t
                    LEFT JOIN `employees` t1 ON (t1.employee_id = t.manager_id)
                    LEFT JOIN `employees` t2 ON (t2.`department_id` = t.department_id)
                    WHERE (t.department_id = '80')
         */
        List<DepartmentVo> departmentVoList = departmentMapper.selectJoinList(DepartmentVo.class, new MPJLambdaWrapper<Department>()
                .selectAll(Department.class)
                .selectAssociation("t1", Employee.class, DepartmentVo::getManager, map -> map
                        .id(Employee::getEmployeeId)
                        .result(Employee::getFirstName))
                .selectCollection("t2", Employee.class, DepartmentVo::getEmployeeList, map2 -> map2
                        .id(Employee::getEmployeeId, Employee::getEmployeeId)
                        .result(Employee::getFirstName, Employee::getFirstName))
                .leftJoin(Employee.class, Employee::getEmployeeId, Department::getManagerId)
                .leftJoin(Employee.class, Employee::getDepartmentId, Department::getDepartmentId)
                .eq(Department::getDepartmentId, "80"));

        departmentVoList.forEach(System.out::println);
    }

    /**
     * 测试MPJLambdaWrapper一对多查询, 不指定实体字段映射
     * DepartmentVo中嵌套List<Employee>和Location
     */
    @Test
    public void testMPJWrapperSelectCollectionNoSpecify() {
        /*
            SQL -> SELECT t.department_id,t.department_name,t.location_id,
                    t1.employee_id,t1.`first_name`,
                    t2.city,t2.street_address
                    FROM departments t LEFT JOIN `employees` t1 ON (t1.`department_id` = t.department_id)
                    LEFT JOIN locations t2 ON (t2.location_id = t.location_id)
                    WHERE (t.department_id = '100')
         */
        List<DepartmentVo> departmentVoList = departmentMapper.selectJoinList(DepartmentVo.class, new MPJLambdaWrapper<Department>()
                .select(Department::getDepartmentId, Department::getDepartmentName, Department::getLocationId)
                .selectCollection(DepartmentVo::getEmployeeList, map -> map
                        .id(Employee::getEmployeeId, Employee::getEmployeeId)
                        .result(Employee::getFirstName))
                .selectAssociation(DepartmentVo::getLocation, locationMap -> locationMap
                        .result(Location::getCity, Location::getCity)
                        .result(Location::getStreetAddress))
                .leftJoin(Employee.class, Employee::getDepartmentId, Department::getDepartmentId)
                .leftJoin(Location.class, Location::getLocationId, Department::getLocationId)
                .eq(Department::getDepartmentId, "100"));

        departmentVoList.forEach(System.out::println);
    }

    /**
     * 测试MPJLambdaWrapper一对多查询, 全部映射, 将关联实体的全部字段查出来
     * DepartmentVo中嵌套List<Employee>
     */
    @Test
    public void testMPJWrapperSelectCollection() {
        /*
            SQL -> SELECT t.department_id,t.department_name,
                    t1.employee_id,t1.`first_name`,t1.`last_name`,t1.`email`,t1.`phone_number`,
                    t1.`hire_date`,t1.`job_id`,t1.`salary`,t1.`commission_pct`,t1.`manager_id`,
                    t1.`department_id` AS joina_department_id
                    FROM departments t
                    LEFT JOIN `employees` t1 ON (t1.`department_id` = t.department_id)
                    WHERE (t.department_id = '20')
         */
        List<DepartmentVo> departmentVoList = departmentMapper.selectJoinList(DepartmentVo.class, new MPJLambdaWrapper<Department>()
                .select(Department::getDepartmentId, Department::getDepartmentName)
                .selectCollection(Employee.class, DepartmentVo::getEmployeeList)
                .leftJoin(Employee.class, Employee::getDepartmentId, Department::getDepartmentId)
                .eq(Department::getDepartmentId, "20"));

        departmentVoList.forEach(System.out::println);
    }

    /**
     * 测试MPJLambdaWrapper一对一查询, 不指定实体字段映射
     * EmployeeVo中嵌套DepartmentVo, DepartmentVo中嵌套Location
     */
    @Test
    public void testMPJWrapperSelectAssociSomeFieldNoSpecify() {
        /*
            SQL -> SELECT t.employee_id,t.`first_name`,t.`department_id`,
                    t3.job_title,
                    t1.department_id AS joina_department_id,t1.department_name,
                    t2.location_id,t2.city
                    FROM `employees` t
                    LEFT JOIN departments t1 ON (t1.department_id = t.`department_id`)
                    LEFT JOIN locations t2 ON (t2.location_id = t1.location_id)
                    LEFT JOIN jobs t3 ON (t3.job_id = t.employee_id)
         */
        List<EmployeeVo> employeeVoList = employeeMapper.selectJoinList(EmployeeVo.class, new MPJLambdaWrapper<Employee>()
                .select(Employee::getEmployeeId, Employee::getFirstName, Employee::getDepartmentId)
                .selectAssociation(EmployeeVo::getDepartmentVo, map -> map
                        .id(Department::getDepartmentId, DepartmentVo::getDepartmentId)
                        .result(Department::getDepartmentName)
                        .association(Location.class, DepartmentVo::getLocation, map2 -> map2
                                .id(Location::getLocationId)
                                .result(Location::getCity)))
                .select(Job::getJobTitle)
                .leftJoin(Department.class, Department::getDepartmentId, Employee::getDepartmentId)
                .leftJoin(Location.class, Location::getLocationId, Department::getLocationId)
                .leftJoin(Job.class, Job::getJobId, Employee::getEmployeeId));

        employeeVoList.forEach(System.out::println);
    }

    /**
     * 测试MPJLambdaWrapper一对一查询未指定带查询实体情况下的部分字段查询
     * 结果接收的不太好
     */
    @Test
    public void testMPJWrapperSelectAssociationSomeFieldNoSpecify() {
        /*
            SQL -> SELECT t.employee_id,t.`first_name`,t.`department_id`,
                    dept.department_id AS joina_department_id,
                    t2.job_title
                    FROM `employees` t
                    LEFT JOIN departments dept ON (dept.department_id = t.employee_id)
                    LEFT JOIN jobs t2 ON (t2.job_id = t.`job_id`)
         */
        List<EmployeeVo> employeeVoList = employeeMapper.selectJoinList(EmployeeVo.class, new MPJLambdaWrapper<Employee>()
                .select(Employee::getEmployeeId, Employee::getFirstName, Employee::getDepartmentId)
                .selectAssociation(EmployeeVo::getDepartment, map -> map
                        .id(Department::getDepartmentId)
                        .result(Job::getJobTitle, Department::getDepartmentName))
                .leftJoin(Department.class, "dept", Department::getDepartmentId, Employee::getEmployeeId)
                .leftJoin(Job.class, Job::getJobId, Employee::getJobId));

        employeeVoList.forEach(System.out::println);
    }

    /**
     * 测试MPJLambdaWrapper一对一查询部分字段
     */
    @Test
    public void testMPJWrapperSelectAssociationSomeField() {
        /*
            SQL -> SELECT t.employee_id,t.`first_name`,t1.department_id,t1.department_name
                    FROM `employees` t LEFT JOIN departments t1 ON (t1.department_id = t.`department_id`)
         */
        List<EmployeeVo> employeeVoList = employeeMapper.selectJoinList(EmployeeVo.class, new MPJLambdaWrapper<Employee>()
                .select(Employee::getEmployeeId, Employee::getFirstName)
                .selectAssociation(Department.class, EmployeeVo::getDepartment, map -> map
                        .id(Department::getDepartmentId)
                        .result(Department::getDepartmentName))
                .leftJoin(Department.class, Department::getDepartmentId, Employee::getDepartmentId));

        employeeVoList.forEach(System.out::println);
    }

    /**
     * 测试MPJLambdaWrapper一对一查询
     */
    @Test
    public void testMPJWrapperSelectAssociation() {
        /*
            SQL -> SELECT t.employee_id,t.`first_name`,t.`department_id`,
                    t1.department_id AS joina_department_id,t1.department_name,
                    t1.manager_id,t1.location_id
                    FROM `employees` t LEFT JOIN departments t1 ON (t1.department_id = t.`department_id`)
         */
        List<EmployeeVo> employeeVoList = employeeMapper.selectJoinList(EmployeeVo.class, new MPJLambdaWrapper<Employee>()
                .select(Employee::getEmployeeId, Employee::getFirstName, Employee::getDepartmentId)
                .selectAssociation(Department.class, EmployeeVo::getDepartment)
                .leftJoin(Department.class, Department::getDepartmentId, Employee::getDepartmentId));

        employeeVoList.forEach(System.out::println);
    }

    /**
     * 测试MPJLambdaWrapper查询
     */
    @Test
    public void testMPJWrapperSelectAs() {
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
     * 测试查询
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
