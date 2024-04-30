package com.sunl19ht.spring6.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

@SpringJUnitConfig(locations = "classpath:beans.xml")
public class JdbcTemplateTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    //查询: 返回对象
    @Test
    public void testSelectQuery() {
        //1. 编写sql语句
        String sql = "select * from t_emp where id = ?";

        //2. 调用jdbcTemplate的方法 传入参数
        /**
         * 1. sql语句
         * 2. 返回值类型
         * 3. 传入参数
         */
        //写法1
//        Emp empResult = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
//            Emp emp = new Emp();
//            emp.setId(rs.getInt("id"));
//            emp.setName(rs.getString("name"));
//            emp.setAge(rs.getInt("age"));
//            emp.setSex(rs.getString("sex"));
//            return emp;
//        }, 2);
//        System.out.println(empResult);
        //写法2
        Emp empResult = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Emp.class), 2);
        System.out.println(empResult);
    }
    //查询: 返回List集合
    @Test
    public void testSelectList() {
        String sql = "select * from t_emp";
        List<Emp> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Emp.class));
        System.out.println(list);
    }

    //查询: 返回单个值
    @Test
    public void testSelectValue() {
        String sql = "select count(*) from t_emp";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println(count);
    }

    @Test //添加 修改 删除
    public void testUpdate() {
        //1. 编写sql语句
//        String sql = "insert into t_emp values(NULL, ?, ?, ?)";    // ? 表示占位符
//
//        //2. 调用jdbcTemplate的方法 传入参数
//        int rows = jdbcTemplate.update(sql,  "东方不败", 20, "未知");  // 参数对应sql语句中的占位符
//        System.out.println(rows);

        //2. 修改
//        String sql = "update t_emp set name = ? where id = ?";
//        int rows = jdbcTemplate.update(sql, "东方不败atguigu", 1);

        //3. 删除
        String sql = "delete from t_emp where id = ?";
        int rows = jdbcTemplate.update(sql, 1);
    }
}
