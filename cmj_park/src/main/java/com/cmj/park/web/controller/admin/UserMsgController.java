package com.cmj.park.web.controller.admin;

import com.cmj.park.domain.vo.AjaxResult;
import com.cmj.park.domain.vo.UserMsg;
import com.cmj.park.service.admin.IUserMsgService;
import com.cmj.park.util.IsOnlyUtil;
import com.cmj.park.util.SecurityUtil;
import com.cmj.park.validation.ValidGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

/**
 * 用户信息修改
 *
 * 权限管理员
 */
@RestController
@RequestMapping("/park/admin/userMsg")
@Validated
@PreAuthorize("hasAnyRole('ROLE_ADMIN_SUPER')")
public class UserMsgController {
    @Autowired
    private IUserMsgService iUserService;

    /**
     * 获取用户信息
     * @param userMsg
     * @return 用户信息
     */
    @GetMapping("/listUser")
    public AjaxResult listUser(UserMsg userMsg) {
        return AjaxResult.pageResult(iUserService.listUser(userMsg));
    }

    /**
     * 删除用户
     * @param ids 用户id
     * @return 操作结果
     */
    @DeleteMapping("/delete/{ids}")
    public AjaxResult delete(@NotEmpty(message = "用户ids不能为空") @PathVariable Integer[] ids) {
        return AjaxResult.updateResult(iUserService.delete(ids), "删除用户");
    }

    /**
     * 添加用户
     * @param userMsg 用户表单
     * @return 操作结果
     */
    @PostMapping("/addUser")
    public AjaxResult addUser(@Validated(ValidGroup.Crud.Create.class) @RequestBody UserMsg userMsg) {
        if (IsOnlyUtil.justOne(iUserService, userMsg) != null) {//判断用户信息是否唯一
            return IsOnlyUtil.justOne(iUserService, userMsg);
        } else {//添加数据
            userMsg.setPassword(SecurityUtil.encryptPassword(userMsg.getPassword()));
            return AjaxResult.updateResult(iUserService.addUser(userMsg), "添加用户");
        }
    }

    /**
     * 添加付费卡
     * @param userMsg 用户表单
     * @return 操作结果
     */
    @PostMapping("/addCard")
    public AjaxResult addCard(@Validated(ValidGroup.Crud.Create.class) @RequestBody UserMsg userMsg) {
        return AjaxResult.updateResult(iUserService.addCard(userMsg), "添加缴费卡");
    }

    /**
     * 充值功能
     * @param userMsg 用户表单
     * @return 操作结果
     */
    @PutMapping("/updateCard")
    public AjaxResult updateCard(@Validated(ValidGroup.Crud.Update.class) @RequestBody UserMsg userMsg) {
        if (userMsg.getCardMoney() < 0) {
            return AjaxResult.error(500, "充值金额不能为负值");
        }
        return AjaxResult.updateResult(iUserService.updateCard(userMsg), "充值");
    }

    /**
     * 更新用户信息
     * @param userMsg 用户表单
     * @return 操作结果
     */
    @PutMapping("/update")
    public AjaxResult update(@Validated(ValidGroup.Crud.Update.class) @RequestBody UserMsg userMsg) {
        if (IsOnlyUtil.justOne(iUserService, userMsg) != null) {//判断用户信息是否唯一
            return IsOnlyUtil.justOne(iUserService, userMsg);
        } else {//添加数据
            userMsg.setPassword(SecurityUtil.encryptPassword(userMsg.getPassword()));
            return AjaxResult.updateResult(iUserService.update(userMsg), "更新用户");
        }
    }
}
