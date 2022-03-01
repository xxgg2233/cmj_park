package com.cmj.park.util;

import com.cmj.park.domain.entity.Admin;
import com.cmj.park.domain.entity.Car;
import com.cmj.park.domain.entity.ParkMsg;
import com.cmj.park.domain.entity.User;
import com.cmj.park.domain.vo.AjaxResult;
import com.cmj.park.domain.vo.UserMsg;
import com.cmj.park.service.ICarService;
import com.cmj.park.service.IParkService;
import com.cmj.park.service.IUserService;
import com.cmj.park.service.admin.IAdminService;
import com.cmj.park.service.admin.IUserMsgService;

/**
 * 校验数据是否唯一
 */
public class IsOnlyUtil {

    /**
     * 检查用户表中是否有相同数据
     */
    public static AjaxResult justOne(IUserService userService, User user) {
        if (userService.selIsOnly("username", user.getUsername(), user.getId())) {
            return AjaxResult.error("用户名已经被注册过了");
        } else if (userService.selIsOnly("phone", user.getPhone(), user.getId())) {
            return AjaxResult.error("手机已经被注册过了");
        } else if (user.getEmail() != "" && user.getEmail() != null &&
                userService.selIsOnly("email", user.getEmail(), user.getId())) {
            return AjaxResult.error("邮箱已经被注册过了");
        }
        return null;
    }

    /**
     * 检查管理员中是否有相同数据
     */
    public static AjaxResult justOne(IAdminService iAdminService, Admin admin) {
        if (iAdminService.selIsOnly("name", admin.getName(), admin.getId())) {
            return AjaxResult.error("用户名已经被注册过了");
        } else if (iAdminService.selIsOnly("phone", admin.getPhone(), admin.getId())) {
            return AjaxResult.error("手机已经被注册过了");
        }
        return null;
    }

    /**
     * 检查用户信息表中是否有相同数据
     */
    public static AjaxResult justOne(IUserMsgService userService, UserMsg userMsg) {
        if (userService.selIsOnly("username", userMsg.getUsername(), userMsg.getId())) {
            return AjaxResult.error("用户名已经被注册过了");
        } else if (userService.selIsOnly("phone", userMsg.getPhone(), userMsg.getId())) {
            return AjaxResult.error("手机已经被注册过了");
        } else if (userMsg.getEmail() != "" && userMsg.getEmail() != null &&
                userService.selIsOnly("email", userMsg.getEmail(), userMsg.getId())) {
            return AjaxResult.error("邮箱已经被注册过了");
        } else {
            return null;
        }
    }

    /**
     * 校验车牌
     */
    public static AjaxResult justOne(ICarService iCarService, Car car) {
        if (iCarService.selIsOnly("car_number", car.getCarNumber(), car.getId())) {
            return AjaxResult.error("该车牌已经被绑定过了");
        }
        return null;
    }

    /**
     * 校验车位信息
     */
    public static AjaxResult justOne(IParkService iParkService, ParkMsg parkMsg) {
        if (iParkService.selIsOnly("park_number", parkMsg.getParkNumber(), parkMsg.getId())) {
            return AjaxResult.error("该车位号已经存在");
        }
        return null;
    }
}
