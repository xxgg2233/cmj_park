package com.cmj.park.dao;

import com.cmj.park.domain.entity.Admin;
import com.cmj.park.domain.entity.CardMoney;
import com.cmj.park.domain.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 用户信息 mapper
 */
@Mapper
public interface UserMapper {

    //根据id查询用户名
    @Select("select username from t_user where id = #{id}")
    public String selUserNameById(Integer id);

    // 根据用户名查询用户
    @Select("select * from cmj_park.t_user where username = #{username}")
    public List<User> selectByUsername(String username);

    // 根据用户名查询管理员
    @Select("select * from cmj_park.t_admin where name = #{username}")
    public List<Admin> selectByAdminName(String username);

    // 添加用户
    @Insert("INSERT INTO cmj_park.t_user (username,realname,password,phone,email)" +
            " VALUES (#{username},#{realname},#{password},#{phone},#{email})")
    public int insert(User user);

    // 更新用户
    @Update("update cmj_park.t_user set username = #{username},realname = #{realname},email = #{email},phone = #{phone} where id = #{id}")
    public int update(User user);

    // 更新用户密码
    @Update("update cmj_park.t_user set password = #{password} where id = #{id}")
    public int updatePwd(@Param("id") Integer id, @Param("password") String password);

    //查询对应值是否存在
    @Select("<script> select count(*) from cmj_park.t_user where ${columnName} = #{username} <if test=\" id != null\"> and id not in (#{id})</if> </script> ")
    public int selOnly(String columnName, String username, Integer id);

    // 根据用户名查询卡信息
    @Select("select * from cmj_park.t_card_money where card_owner = #{username}")
    public List<CardMoney> selCMByName(String username);

}

