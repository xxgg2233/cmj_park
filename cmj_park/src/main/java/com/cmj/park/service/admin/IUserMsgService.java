package com.cmj.park.service.admin;

import com.cmj.park.domain.vo.UserMsg;

import java.util.List;

/**
 * 用户修改服务接口
 */
public interface IUserMsgService {
    /**
     * 根据关键词查询
     * @param userMsg 用户表单
     * @return 用户信息
     */
    List<UserMsg> listUser(UserMsg userMsg);

    /**
     * 删除用户
     * @param ids 用户id
     * @return 变化条目数
     */
    int delete(Integer[] ids);

    /**
     * 添加用户
     * @param userMsg 用户表单
     * @return 操作条目数
     */
    int addUser(UserMsg userMsg);

    /**
     * 添加用户
     * @param userMsg 用户表单
     * @return 操作条目数
     */
    int addCard(UserMsg userMsg);

    /**
     * 更新用户信息
     * @param userMsg 用户表单
     * @return 操作条目数
     */
    int update(UserMsg userMsg);

    /**
     * 充值
     * @param userMsg 用户表单
     * @return 操作条目数
     */
    int updateCard(UserMsg userMsg);

    /**
     * 查询是否存在字段数据
     * @param columnName 字段名
     * @param user 用户名
     * @param id 用户id
     * @return true or false
     */
    public boolean selIsOnly(String columnName,String user,Integer id);
}
