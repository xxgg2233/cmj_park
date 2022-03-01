package com.cmj.park.dao.admin;

import com.cmj.park.domain.entity.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * 管理员信息 Mapper
 *
 */
@Mapper
public interface AdminMapper {

    // 根据关键字查询
    @Select("<script> select * from cmj_park.t_admin <where> <if test=\" name != null\"> and name like CONCAT('%', #{name}, '%')</if> </where> </script> ")
    public List<Admin> selectNew(Admin admin);

    // 添加管理员信息
    @Insert("INSERT INTO cmj_park.t_admin(name,password,realname,phone) VALUES(#{name}, #{password}, #{realName}, #{phone}) ")
    public int insert(Admin admin);

    // 通过id删除管理员
    @Delete("DELETE FROM cmj_park.t_admin WHERE id = #{id}")
    public int delete(Integer id);

    // 更新管理员
    @Update("update cmj_park.t_admin set name=#{name}, password=#{password}, realname=#{realName}, phone=#{phone} where id = #{id}")
    public int update(Admin admin);

    //查询对应值是否存在
    @Select("<script> select count(*) from cmj_park.t_admin where ${columnName} = #{admin} <if test=\" id != null\"> and id not in (#{id})</if> </script> ")
    public int selOnly(String columnName, String admin, Integer id);
}
