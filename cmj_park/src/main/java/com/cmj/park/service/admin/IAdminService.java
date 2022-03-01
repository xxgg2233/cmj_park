package com.cmj.park.service.admin;

import com.cmj.park.domain.entity.Admin;

import java.util.List;

/**
 * 管理员实现接口
 */
public interface IAdminService {
    /**
     * 根据关键词查询
     * @param admin 管理员表单
     * @return admin
     */
    List<Admin> listAdmin(Admin admin);

    /**
     * 根据id删除
     * @param ids 单个或多个id
     * @return 变化行数
     */
    int delete(Integer[] ids);

    /**
     * 添加管理员
     * @param admin 管理员表单
     * @return 变化行数
     */
    int add(Admin admin);

    /**
     * 更新管理员
     * @param admin 管理员表单
     * @return 变化行数
     */
    int update(Admin admin);

    /**
     * 查询是否存在字段数据
     * @param columnName 字段名
     * @param carNumber 车牌号
     * @param id id
     * @return true or false
     */
    public boolean selIsOnly(String columnName,String carNumber,Integer id);
}
