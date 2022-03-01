package com.cmj.park.dao;

import com.cmj.park.domain.entity.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 权限查询mapper
 *
 */
@Mapper
public interface RoleMapper {

    // 用户id查询t_user_role中role_id 再查询出role中对应id的权限
    @Select("select * from t_role where id in(select role_id from t_user_role where user_id = #{id});")
    public List<Role> selUserRole(Integer id);

    // 管理员id查询t_user_role中role_id 再查询出role中对应id的权限
    @Select("select * from t_role where id in(select role_id from t_admin_role where admin_id = #{id});")
    public List<Role> selAdminRole(Integer id);

    //添加用户id到t_user_role中
    @Insert("insert into t_user_role(user_id,role_id) values(#{id},2)")
    public int insUserRole(Integer id);

    //添加管理员id到t_user_role中
    @Insert("insert into t_admin_role(admin_id,role_id) values(#{id},1)")
    public int insAdminRole(Integer id);
}

