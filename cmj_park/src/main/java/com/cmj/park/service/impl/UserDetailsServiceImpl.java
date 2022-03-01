package com.cmj.park.service.impl;


import com.cmj.park.constant.RespCode;
import com.cmj.park.dao.RoleMapper;
import com.cmj.park.domain.entity.Admin;
import com.cmj.park.domain.entity.Role;
import com.cmj.park.domain.entity.User;
import com.cmj.park.domain.vo.LoggedInUser;
import com.cmj.park.exception.CustomException;
import com.cmj.park.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 用户验证服务实现类
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    // 登录类型
    private static final Integer COMMON_LOGIN_TYPE = 0;
    private static final Integer ADMIN_LOGIN_TYPE = 1;

    @Autowired
    private IUserService userService;
    @Autowired
    private RoleMapper roleMapper;

    /**
     *
     * @param username 输入用户名
     * @return token
     * @throws UsernameNotFoundException 账号不存在
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String type = username.substring(username.length()-1);
        String name = username.substring(0,username.length()-1);

        if (type.equals("u")){//判断类型 u为用户
            User user = userService.getByUsername(name);
            if (user == null)//查询不到说明账号不存在
                throw new CustomException("账号不存在", RespCode.LOGIN_FAILURE);

            //设置用户权限
            List<Role> role = roleMapper.selUserRole(user.getId());
            LoggedInUser loggedInUser = new LoggedInUser(UUID.randomUUID().toString(), user.getId(), name, user.getPassword(), role, COMMON_LOGIN_TYPE);
            return loggedInUser;
        }else {//否则是管理员
            Admin admin = userService.getByAdminName(name);
            if (admin == null)//查询不到说明账号不存在
                throw new CustomException("账号不存在", RespCode.LOGIN_FAILURE);

            //设置管理员权限
            List<Role> role = roleMapper.selAdminRole(admin.getId());
            LoggedInUser loggedInUser = new LoggedInUser(UUID.randomUUID().toString(), admin.getId(), name, admin.getPassword(), role, ADMIN_LOGIN_TYPE);
            return loggedInUser;
        }
    }
}
