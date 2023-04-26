package com.atguigu.mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Litway
 * @version 1.0
 */
@Data
@TableName(value = "departments")
public class Department {
    /**
     *
     */
    @TableId(value = "department_id")
    private Integer departmentId;

    /**
     *
     */
    private String departmentName;

    /**
     *
     */
    private Integer managerId;

    /**
     *
     */
    private Integer locationId;
}

