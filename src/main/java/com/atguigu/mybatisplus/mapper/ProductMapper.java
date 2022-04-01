package com.atguigu.mybatisplus.mapper;

import com.atguigu.mybatisplus.pojo.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 产品映射器
 *
 * @author DELL_
 * @date 2022/04/01
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {


}
