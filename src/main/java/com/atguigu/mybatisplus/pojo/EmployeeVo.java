package com.atguigu.mybatisplus.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author Litway
 * @version 1.0
 */
@Data
public class EmployeeVo {

    private Integer employeeId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Date hireDate;

    private String jobId;

    private Double salary;

    private Double commissionPct;

    private Integer managerId;

    private Integer departmentId;

    private String jobTitle;

    private Department department;

}
