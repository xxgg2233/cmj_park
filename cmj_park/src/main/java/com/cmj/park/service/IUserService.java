package com.cmj.park.service;

import com.cmj.park.domain.entity.Admin;
import com.cmj.park.domain.entity.CardMoney;
import com.cmj.park.domain.entity.User;

/**
 * 用户服务接口
 *
 */
public interface IUserService {
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    public User getByUsername(String username);

    /**
     * 根据用户名查询管理员
     * @param username 管理员名
     * @return 管理员信息
     */
    public Admin getByAdminName(String username);

    /**
     * 更新用户
     * @param user 用户表单
     * @return 变化条目数
     */
    public int update(User user);

    /**
     * 更新用户密码
     * @param id 用户id
     * @param password 密码
     * @return 变化条目数
     */
    public int updatePwd(Integer id, String password);

    /**
     * 添加用户
     * @param user 用户表单
     * @return 变化条目数
     */
    public int insert(User user);

    /**
     * 查询是否存在字段数据
     * @param columnName 字段名
     * @param username 校对值
     * @param id 用户id
     * @return true or false
     */
    public boolean selIsOnly(String columnName,String username,Integer id);

    /**
     * 查询卡信息
     * @param username 用户名
     * @return 缴费卡信息
     */
    public CardMoney selCMByName(String username);

}

