package com.atguigu.mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @author Litway
 * @version 1.0
 */
@Data
@TableName(value = "`employees`")
public class Employee {
    @TableId(value = "employee_id")
    private Integer employeeId;

    @TableField(value = "`first_name`")
    private String firstName;

    @TableField(value = "`last_name`")
    private String lastName;

    @TableField(value = "`email`")
    private String email;

    @TableField(value = "`phone_number`")
    private String phoneNumber;

    @TableField(value = "`hire_date`")
    private Date hireDate;

    @TableField(value = "`job_id`")
    private String jobId;

    @TableField(value = "`salary`")
    private Double salary;

    @TableField(value = "`commission_pct`")
    private Double commissionPct;

    @TableField(value = "`manager_id`")
    private Integer managerId;

    @TableField(value = "`department_id`")
    private Integer departmentId;
}