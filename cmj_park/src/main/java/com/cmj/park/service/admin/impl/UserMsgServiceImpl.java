package com.cmj.park.service.admin.impl;

import com.cmj.park.dao.RoleMapper;
import com.cmj.park.dao.UserMapper;
import com.cmj.park.dao.admin.UserMsgMapper;
import com.cmj.park.domain.vo.UserMsg;
import com.cmj.park.service.admin.IUserMsgService;
import com.cmj.park.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 用户信息修改服务实现类
 */
@Service
public class UserMsgServiceImpl implements IUserMsgService {
    @Autowired
    private UserMsgMapper userMsgMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 根据关键字查询用户信息
     * @param userMsg 用户表单
     * @return 用户信息
     */
    @Override
    public List<UserMsg> listUser(UserMsg userMsg) {
        PageUtil.start();
        List<UserMsg> userMsgList = userMsgMapper.selectNew(userMsg);
        System.out.println(userMsgList);
        return userMsgList;
    }

    /**
     * 根据id删除用户
     * @param ids 用户id
     * @return 变化条目数
     */
    @Transactional
    @Override
    public int delete(Integer[] ids) {
        int result = 0;
        for (Integer id : ids) {
            //删除卡号和用户信息
            result += userMsgMapper.delete(id);
        }
        return result;
    }

    /**
     * 添加用户
     * @param userMsg 用户表单
     * @return 变化条目数
     */
    @Transactional
    @Override
    public int addUser(UserMsg userMsg) {
        int result = userMsgMapper.insert(userMsg);
        if (result != 0){
            roleMapper.insUserRole(userMapper.selectByUsername(userMsg.getUsername()).get(0).getId());
        }
        return result;
    }

    /**
     * 添加缴费卡
     * @param userMsg 用户表单
     * @return 变化条目数
     */
    @Transactional
    @Override
    public int addCard(UserMsg userMsg) {
        //随机截取8位UUID作为卡号 余额默认为0
        int i = new Random().nextInt(9);
        userMsg.setCardNumber(UUID.randomUUID().toString().replaceAll("-","").substring(i, i+8));
        userMsg.setCardMoney(0);
        return userMsgMapper.insertCard(userMsg);
    }

    /**
     * 更新用户信息
     * @param userMsg 用户表单
     * @return 变化条目数
     */
    @Transactional
    @Override
    public int update(UserMsg userMsg) {
        return userMsgMapper.update(userMsg);
    }

    /**
     * 给缴费卡充值，保留两位小数
     * @param userMsg 用户表单
     * @return 变化条目数
     */
    @Transactional
    @Override
    public int updateCard(UserMsg userMsg) {
        return userMsgMapper.updateCard(userMsg);
    }

    /**
     * 查询是否是唯一值
     * @param columnName 字段名
     * @param user 用户名
     * @param id 用户id
     * @return true or false
     */
    @Override
    public boolean selIsOnly(String columnName, String user, Integer id) {
        if (userMsgMapper.selOnly(columnName, user, id) != 0) {
            return true;
        } else {
            return false;
        }
    }
}
