package com.cmj.park.service.admin.impl;

import com.cmj.park.dao.RoleMapper;
import com.cmj.park.dao.UserMapper;
import com.cmj.park.dao.admin.AdminMapper;
import com.cmj.park.domain.entity.Admin;
import com.cmj.park.service.admin.IAdminService;
import com.cmj.park.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 管理员实现类
 */
@Service
public class AdminServiceImpl implements IAdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 通过关键字查询
     * @param admin 管理员表单
     * @return 管理员信息
     */
    @Override
    public List<Admin> listAdmin(Admin admin) {
        PageUtil.start();
        List<Admin> adminList = adminMapper.selectNew(admin);
        return adminList;
    }

    /**
     * 遍历全部id进行删除操作
     * @param ids 单个或多个id
     * @return 变化条目数
     */
    @Transactional
    @Override
    public int delete(Integer[] ids) {
        int result = 0;
        for (Integer id : ids) {
            result += adminMapper.delete(id);
        }
        return result;
    }

    /**
     * 添加管理员
     * @param admin 管理员表单
     * @return 变化条目数
     */
    @Transactional
    @Override
    public int add(Admin admin) {
        int result = adminMapper.insert(admin);
        if (result != 0){
            //添加用户权限
            roleMapper.insAdminRole(userMapper.selectByAdminName(admin.getName()).get(0).getId());
        }
        return result;
    }

    /**
     * 更新管理员
     * @param admin 管理员表单
     * @return 变化条目数
     */
    @Transactional
    @Override
    public int update(Admin admin) {
        return adminMapper.update(admin);
    }

    /**
     * 根据字段名判断是否在数据库中是唯一值
     * @param columnName 字段名
     * @param admin 管理员表单
     * @param id id
     * @return true or false
     */
    @Override
    public boolean selIsOnly(String columnName, String admin, Integer id) {
        if (adminMapper.selOnly(columnName, admin, id) != 0) {
            return true;
        } else {
            return false;
        }
    }
}
