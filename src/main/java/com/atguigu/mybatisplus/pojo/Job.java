package com.atguigu.mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Litway
 * @version 1.0
 */
@Data
@TableName(value = "jobs")
public class Job {
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
}

