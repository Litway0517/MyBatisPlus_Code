package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.mapper.ProductMapper;
import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.Product;
import com.atguigu.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * MybatisPlus配置测试
 *
 * @author DELL_
 * @date 2022/03/31
 */
@SpringBootTest
class MyBatisPlusPluginsTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    // 模拟小李更改产品价格
    @Test
    public void testProduct() {
        // 小李查询价格
        Product productLi = productMapper.selectById(1);
        System.out.println("小李查询的价格 -> " + productLi.getPrice());
        // 小王查询价格
        Product productWang = productMapper.selectById(1);
        System.out.println("小王查询的价格 -> " + productWang.getPrice());

        // 小李将价格 +50
        productLi.setPrice(productLi.getPrice() + 50);
        productMapper.updateById(productLi);

        // 小王将价格 -30
        productWang.setPrice(productWang.getPrice() - 30);
        int i = productMapper.updateById(productWang);
        if (i == 0) {
            // 说明修改价格失败, 重试
            // 一定要先查询, 这样也好理解. 因为前端的界面就是先查询
            Product productWangNew = productMapper.selectById(1);
            productWangNew.setPrice(productWangNew.getPrice() - 30);
            productMapper.updateById(productWangNew);
        }


        // 老板查询价格 -> 这里是70. 因为小王在小李后面就进行了查询
        Product productBoss = productMapper.selectById(1);
        System.out.println("老板查询的价格 -> " + productBoss.getPrice());
    }

    /**
     * 测试自定义的查询方法, 搭配MybatisPlus的分页插件, 自定义方法如果需要实现分页功能, 要求第一个参数必须是Page类型对象
     */
    @Test
    public void testSelectPageVo() {
        /*
            SQL -> select uid, user_name, age, email, is_deleted from `t_user` where is_deleted = '0'
                    and age > ? LIMIT ?,?

         */
        // long current参数是当前页, size是每页中记录的数目
        Page<User> userPage = new Page<User>(2, 3);
        // 传入参数
        Page<User> pageVo = userMapper.selectPageVo(userPage, 20);
        System.out.println("总页数 -> " + pageVo.getPages());
        System.out.println("总记录数 -> " + pageVo.getTotal());
        System.out.println("是否有下一页 -> " + pageVo.hasNext());
        System.out.println("当前页记录 -> " + pageVo.getRecords());
        System.out.println(pageVo);
    }


    /**
     * 测试分页查询, MybatisPlus的分页功能, 需要配置分页插件, 搭配LambdaQueryWrapper查询器
     */
    @Test
    public void testSelectByPage() {
        /*
            SQL -> SELECT uid,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 LIMIT ?,?
            分页的参数是: 该页索引, 每页中的条数. 从index处开始的后面一页
                比如一页3条, 那么第三页的参数为 6, 3
         */
        Page<User> userPage = new Page<User>(2, 3);
        // 设置条件
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<User>();
        userLambdaQueryWrapper.like(User::getName, "admin");

        // 数据都放在了page里面 本次查询不使用条件
        Page<User> page = userMapper.selectPage(userPage, null);
        System.out.println("总页数 -> " + page.getPages());
        System.out.println("总记录数 -> " + page.getTotal());
        System.out.println("是否有下一页 -> " + page.hasNext());
        System.out.println("当前页记录 -> " + page.getRecords());
        System.out.println(page);
    }

    @Test
    void myBatisPlusInterceptor() {

    }
}
