package com.cmj.park.service.impl;

import com.cmj.park.constant.RespCode;
import com.cmj.park.dao.RoleMapper;
import com.cmj.park.dao.UserMapper;
import com.cmj.park.domain.entity.Admin;
import com.cmj.park.domain.entity.CardMoney;
import com.cmj.park.domain.entity.User;
import com.cmj.park.exception.CustomException;
import com.cmj.park.service.IUserService;
import com.cmj.park.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 用户服务实现类
 */
@Service
@Transactional
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public User getByUsername(String username) {
        List<User> users = userMapper.selectByUsername(username);
        if (CollectionUtils.isEmpty(users))
            return null;
        return users.get(0);
    }

    /**
     * 根据管理员名查询管理员
     * @param adminName 管理员名
     * @return 管理员信息
     */
    @Override
    public Admin getByAdminName(String adminName) {
        List<Admin> admins = userMapper.selectByAdminName(adminName);
        if (CollectionUtils.isEmpty(admins))
            return null;
        return admins.get(0);
    }

    /**
     * 更新用户信息
     * @param user 用户表单
     * @return 变化条目数
     */
    @Override
    @Transactional
    public int update(User user) {
        String username = SecurityUtil.getUsername();
        if (!username.equals(user.getUsername()))
            throw new CustomException("不能更新他人信息", RespCode.FORBIDDEN);
        return userMapper.update(user);
    }

    /**
     * 更新密码
     * @param id 用户id
     * @param password 密码
     * @return 变化条目数
     */
    @Override
    @Transactional
    public int updatePwd(Integer id, String password) {
        String username = SecurityUtil.getUsername();
        if (!username.equals(userMapper.selUserNameById(id)))
            throw new CustomException("不能更新他人信息", RespCode.FORBIDDEN);
        return userMapper.updatePwd(id, SecurityUtil.encryptPassword(password));
    }

    /**
     * 添加用户
     * @param user 用户表单
     * @return 变化条目数
     */
    @Override
    @Transactional
    public int insert(User user) {
        int result = userMapper.insert(user);
        if (result!=0){
            //添加用户权限
            roleMapper.insUserRole(getByUsername(user.getUsername()).getId());
        }
        return result;
    }

    /**
     * 查询是否是唯一值
     * @param columnName 字段名
     * @param username 用户名
     * @param id id
     * @return true or false
     */
    @Override
    public boolean selIsOnly(String columnName,String username,Integer id) {
        if (userMapper.selOnly(columnName,username,id) != 0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 通过用户名查询缴费卡信息
     * @param username 用户名
     * @return 缴费卡信息
     */
    @Override
    public CardMoney selCMByName(String username) {
        List<CardMoney> cardMoney = userMapper.selCMByName(username);
        if (CollectionUtils.isEmpty(cardMoney))
            return null;
        return cardMoney.get(0);
    }
}
