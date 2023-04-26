package com.atguigu.mybatisplus.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author Litway
 * @version 1.0
 */
@Data
public class DepartmentVo {

    private Integer departmentId;

    private String departmentName;

    private Integer managerId;

    private Integer locationId;

    private List<Employee> employeeList;
}

