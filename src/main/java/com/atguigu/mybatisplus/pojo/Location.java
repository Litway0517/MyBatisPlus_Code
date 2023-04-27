package com.atguigu.mybatisplus.pojo;

import lombok.Data;

@Data
public class Location {

    private Integer locationId;

    private String streetAddress;

    private String postalCode;

    private String city;

    private String stateProvince;

    private String countryId;
}

