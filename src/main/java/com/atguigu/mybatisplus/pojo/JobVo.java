package com.atguigu.mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Litway
 * @version 1.0
 */
@Data
public class JobVo {

    /**
     *
     */
    @TableId(value = "job_id")
    private String jobId;

    /**
     *
     */
    private String jobTitle;

    /**
     *
     */
    private Integer minSalary;

    /**
     *
     */
    private Integer maxSalary;

    /**
     * 用户部门
     */
    private Department department;

}
