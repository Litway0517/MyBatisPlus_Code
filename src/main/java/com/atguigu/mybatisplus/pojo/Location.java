package com.atguigu.mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("locations")
public class Location {

    private Integer locationId;

    private String streetAddress;

    private String postalCode;

    private String city;

    private String stateProvince;

    private String countryId;
}

